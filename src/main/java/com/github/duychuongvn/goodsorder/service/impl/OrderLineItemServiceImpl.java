package com.github.duychuongvn.goodsorder.service.impl;

import com.github.duychuongvn.core.otp.OTPGeneratorUtil;
import com.github.duychuongvn.goodsorder.config.Constants;
import com.github.duychuongvn.goodsorder.domain.ExchangeRate;
import com.github.duychuongvn.goodsorder.domain.Order;
import com.github.duychuongvn.goodsorder.domain.OrderLineItem;
import com.github.duychuongvn.goodsorder.domain.User;
import com.github.duychuongvn.goodsorder.domain.enumeration.DeliveryStatus;
import com.github.duychuongvn.goodsorder.domain.enumeration.OrderStatus;
import com.github.duychuongvn.goodsorder.exception.ResourceNotFoundException;
import com.github.duychuongvn.goodsorder.repository.OrderLineItemRepository;
import com.github.duychuongvn.goodsorder.service.ExchangeRateService;
import com.github.duychuongvn.goodsorder.service.OrderLineItemService;
import com.github.duychuongvn.goodsorder.service.OrderService;
import com.github.duychuongvn.goodsorder.service.UserService;
import com.github.duychuongvn.goodsorder.service.dto.OrderLineItemDto;
import com.github.duychuongvn.ordering.webparser.dto.OriginProduct;
import com.github.duychuongvn.ordering.webparser.service.ParserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link OrderLineItem}.
 */
@Service
@Transactional
public class OrderLineItemServiceImpl implements OrderLineItemService {

    private final Logger log = LoggerFactory.getLogger(OrderLineItemServiceImpl.class);

    private final OrderLineItemRepository orderLineItemRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ExchangeRateService exchangeRateService;

    public OrderLineItemServiceImpl(OrderLineItemRepository orderLineItemRepository) {
        this.orderLineItemRepository = orderLineItemRepository;
    }

    /**
     * Save a orderLineItem.
     *
     * @param orderLineItem the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OrderLineItem save(OrderLineItem orderLineItem) {
        log.debug("Request to save OrderLineItem : {}", orderLineItem);
        return orderLineItemRepository.save(orderLineItem);
    }

    /**
     * Get all the orderLineItems.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderLineItem> findAll() {
        log.debug("Request to get all OrderLineItems");
        return orderLineItemRepository.findAll();
    }


    /**
     * Get one orderLineItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderLineItem> findOne(Long id) {
        log.debug("Request to get OrderLineItem : {}", id);
        return orderLineItemRepository.findById(id);
    }

    /**
     * Delete the orderLineItem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderLineItem : {}", id);
        orderLineItemRepository.deleteById(id);
    }

    @Override
    public Order createOrUpdateOrder(Order currentOrder, String productUrl) {
        if (currentOrder == null) {
            currentOrder = orderService.createNewOrder();
        }
        OriginProduct originProduct = ParserFactory.extractProduct(productUrl);

        OrderLineItem item = new OrderLineItem();
        currentOrder.addOrderLineItems(item);
        item.setReferenceUrl(productUrl);
        item.setGoodsId(originProduct.getId());
        item.setGoodsName(originProduct.getName());
        item.setImages(originProduct.getImages());
        item.setOriginPrice(originProduct.getOriginPrice());
        item.setSalePrice(originProduct.getSalePrice());
        item.setSource(originProduct.getSource());
        item.setTax(originProduct.getTax());
        item.setQuantity(1);
        item.setTotalPay((item.getSalePrice().add(item.getTax())).multiply(BigDecimal.valueOf(item.getQuantity())));
        save(item);
        calculatePaidInVnd(currentOrder);
        return orderService.save(currentOrder);
    }

    @Override
    @Transactional
    public Order update(OrderLineItemDto orderLineItem) {

        OrderLineItem item = orderLineItemRepository.getOne(orderLineItem.getId());
        if (item == null) {
            throw new ResourceNotFoundException("OrderLineItem not found: " + orderLineItem.getId());
        }

        item.setSize(orderLineItem.getSize());
        item.setQuantity(orderLineItem.getQuantity());
        item.setTotalPay((item.getSalePrice().add(item.getTax())).multiply(BigDecimal.valueOf(item.getQuantity())));
        Order order = item.getOrder();
        calculatePaidInVnd(order);
        order.setEstimatedDeliverDate(LocalDate.now().plusDays(30));
        order.setPackingDate(LocalDate.now().plusDays(7));

        orderLineItemRepository.save(item);
        orderService.save(order);
        return order;
    }

    private void calculatePaidInVnd(Order order) {
        BigDecimal totalPayJPY = order.getOrderLineItems()
            .stream()
            .map(OrderLineItem::getTotalPay)
            .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        order.setTotalJpyPrice(totalPayJPY);
        order.setDeliveryFeeVnd(totalPayJPY.multiply(order.getExchangeRate()).multiply(BigDecimal.valueOf(0.5)));// TODO fee is 50%
        order.setTotalPayVnd(totalPayJPY.multiply(order.getExchangeRate()).add(order.getDeliveryFeeVnd()));
    }
}
