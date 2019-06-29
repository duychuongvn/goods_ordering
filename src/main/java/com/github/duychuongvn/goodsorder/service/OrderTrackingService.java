package com.github.duychuongvn.goodsorder.service;

import com.github.duychuongvn.goodsorder.domain.OrderTracking;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link OrderTracking}.
 */
public interface OrderTrackingService {

    /**
     * Save a orderTracking.
     *
     * @param orderTracking the entity to save.
     * @return the persisted entity.
     */
    OrderTracking save(OrderTracking orderTracking);

    /**
     * Get all the orderTrackings.
     *
     * @return the list of entities.
     */
    List<OrderTracking> findAll();


    /**
     * Get the "id" orderTracking.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderTracking> findOne(Long id);

    /**
     * Delete the "id" orderTracking.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
