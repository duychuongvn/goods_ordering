package com.github.duychuongvn.goodsorder.service;

import com.github.duychuongvn.goodsorder.domain.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Order}.
 */
public interface OrderService {

    /**
     * Save a order.
     *
     * @param order the entity to save.
     * @return the persisted entity.
     */
    Order save(Order order);

    Order createNewOrder();

    /**
     * Get all the orders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Order> findAll(Pageable pageable);

    /**
     * Get all the orders by current user.
     *
     * @return the list of entities.
     */
    List<Order> findByCurrentUser();
    /**
     * Get the "id" order.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Order> findOne(Long id);

    /**
     * Delete the "id" order.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
