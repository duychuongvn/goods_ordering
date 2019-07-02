package com.github.duychuongvn.goodsorder.service.impl;

import com.github.duychuongvn.core.otp.OTPGeneratorUtil;
import com.github.duychuongvn.goodsorder.config.Constants;
import com.github.duychuongvn.goodsorder.domain.*;
import com.github.duychuongvn.goodsorder.domain.enumeration.DeliveryStatus;
import com.github.duychuongvn.goodsorder.domain.enumeration.OrderStatus;
import com.github.duychuongvn.goodsorder.repository.OrderRepository;
import com.github.duychuongvn.goodsorder.repository.OrderScheduleRepository;
import com.github.duychuongvn.goodsorder.repository.ShippingAddressRepository;
import com.github.duychuongvn.goodsorder.service.ExchangeRateService;
import com.github.duychuongvn.goodsorder.service.OrderService;
import com.github.duychuongvn.goodsorder.service.UserService;
import com.github.duychuongvn.goodsorder.web.rest.errors.OrderScheduleNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Order}.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ExchangeRateService exchangeRateService;
    @Autowired
    private ShippingAddressRepository shippingAddressRepository;
    @Autowired
    private OrderScheduleRepository orderScheduleRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Save a order.
     *
     * @param order the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Order save(Order order) {
        log.debug("Request to save Order : {}", order);
        return orderRepository.save(order);
    }

    @Override
    public Order createNewOrder() {
        OrderSchedule orderSchedule = orderScheduleRepository.findOpenOrderSchedule();
        if (orderSchedule == null) {
            throw new OrderScheduleNotFoundException();
        }
        Order currentOrder = new Order();
        currentOrder.setPaymentCode(Constants.KEY_ORDER + OTPGeneratorUtil.generateOTP8Digits("ORDER"));
        ExchangeRate exchangeRate = exchangeRateService.getCurrentExchangeRate();
        currentOrder.setExchangeRateId(exchangeRate.getId());
        currentOrder.setExchangeRate(exchangeRate.getRate());
        currentOrder.setStatus(OrderStatus.PENDING);
        currentOrder.setDeliveryStatus(DeliveryStatus.INIT);
        currentOrder.setCreatedAt(ZonedDateTime.now());
        currentOrder.setOrderDate(ZonedDateTime.now());
        currentOrder.setEstimatedDeliverDate(orderSchedule.getExpectedDeliveryDate());
        currentOrder.setPackingDate(orderSchedule.getExpectedPackingDate());
        currentOrder.setDepositedVnd(BigDecimal.ZERO);
        currentOrder.setPaidVnd(BigDecimal.ZERO);
        Optional<User> currentUser = userService.getUserWithAuthorities();

        currentUser.ifPresent(currentOrder::setUser);

        addShippingAddress(currentOrder);

        orderSchedule.setCurrentOrderNumber(orderSchedule.getCurrentOrderNumber() + 1);
        orderScheduleRepository.save(orderSchedule);
        return currentOrder;
    }

    private void addShippingAddress(Order currentOrder) {
        List<ShippingAddress> shippingAddresses = shippingAddressRepository.findByUserIsCurrentUser();
        shippingAddresses.stream().filter(ShippingAddress::isDefaultAddress)
            .findFirst()
            .ifPresent(shippingAddress -> {
                currentOrder.setAddress1(shippingAddress.getAddress1());
                currentOrder.setAddress2(shippingAddress.getAddress2());
                currentOrder.setCity(shippingAddress.getCity());
                currentOrder.setDistrict(shippingAddress.getDistrict());
                currentOrder.setZipCode(shippingAddress.getZipCode());
                currentOrder.setPhone1(shippingAddress.getPhone1());
                currentOrder.setPhone2(shippingAddress.getPhone2());
                currentOrder.setEmail(shippingAddress.getEmail());
            });
    }

    /**
     * Get all the orders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Order> findAll(Pageable pageable) {
        log.debug("Request to get all Orders");
        return orderRepository.findAll(pageable);
    }

    @Override
    public List<Order> findByCurrentUser() {
        return orderRepository.findByUserIsCurrentUser();
    }

    /**
     * Get one order by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findOne(Long id) {
        log.debug("Request to get Order : {}", id);
        return orderRepository.findById(id);
    }

    /**
     * Delete the order by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        orderRepository.deleteById(id);
    }
}
