package com.github.duychuongvn.goodsorder.service.impl;

import com.github.duychuongvn.goodsorder.service.OrderTrackingService;
import com.github.duychuongvn.goodsorder.domain.OrderTracking;
import com.github.duychuongvn.goodsorder.repository.OrderTrackingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link OrderTracking}.
 */
@Service
@Transactional
public class OrderTrackingServiceImpl implements OrderTrackingService {

    private final Logger log = LoggerFactory.getLogger(OrderTrackingServiceImpl.class);

    private final OrderTrackingRepository orderTrackingRepository;

    public OrderTrackingServiceImpl(OrderTrackingRepository orderTrackingRepository) {
        this.orderTrackingRepository = orderTrackingRepository;
    }

    /**
     * Save a orderTracking.
     *
     * @param orderTracking the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OrderTracking save(OrderTracking orderTracking) {
        log.debug("Request to save OrderTracking : {}", orderTracking);
        return orderTrackingRepository.save(orderTracking);
    }

    /**
     * Get all the orderTrackings.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderTracking> findAll() {
        log.debug("Request to get all OrderTrackings");
        return orderTrackingRepository.findAll();
    }


    /**
     * Get one orderTracking by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderTracking> findOne(Long id) {
        log.debug("Request to get OrderTracking : {}", id);
        return orderTrackingRepository.findById(id);
    }

    /**
     * Delete the orderTracking by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderTracking : {}", id);
        orderTrackingRepository.deleteById(id);
    }
}
