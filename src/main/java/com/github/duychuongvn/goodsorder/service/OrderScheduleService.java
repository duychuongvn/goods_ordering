package com.github.duychuongvn.goodsorder.service;

import com.github.duychuongvn.goodsorder.domain.OrderSchedule;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link OrderSchedule}.
 */
public interface OrderScheduleService {

    /**
     * Save a orderSchedule.
     *
     * @param orderSchedule the entity to save.
     * @return the persisted entity.
     */
    OrderSchedule save(OrderSchedule orderSchedule);

    /**
     * Get all the orderSchedules.
     *
     * @return the list of entities.
     */
    List<OrderSchedule> findAll();


    /**
     * Get the "id" orderSchedule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderSchedule> findOne(Long id);

    /**
     * Delete the "id" orderSchedule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
