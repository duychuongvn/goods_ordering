package com.github.duychuongvn.goodsorder.service;

import com.github.duychuongvn.goodsorder.domain.OrderLineItem;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link OrderLineItem}.
 */
public interface OrderLineItemService {

    /**
     * Save a orderLineItem.
     *
     * @param orderLineItem the entity to save.
     * @return the persisted entity.
     */
    OrderLineItem save(OrderLineItem orderLineItem);

    /**
     * Get all the orderLineItems.
     *
     * @return the list of entities.
     */
    List<OrderLineItem> findAll();


    /**
     * Get the "id" orderLineItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderLineItem> findOne(Long id);

    /**
     * Delete the "id" orderLineItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
