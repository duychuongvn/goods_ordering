package com.github.duychuongvn.goodsorder.service.impl;

import com.github.duychuongvn.goodsorder.service.OrderLineItemService;
import com.github.duychuongvn.goodsorder.domain.OrderLineItem;
import com.github.duychuongvn.goodsorder.repository.OrderLineItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
