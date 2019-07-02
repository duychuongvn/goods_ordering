package com.github.duychuongvn.goodsorder.service.impl;

import com.github.duychuongvn.goodsorder.service.OrderScheduleService;
import com.github.duychuongvn.goodsorder.domain.OrderSchedule;
import com.github.duychuongvn.goodsorder.repository.OrderScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link OrderSchedule}.
 */
@Service
@Transactional
public class OrderScheduleServiceImpl implements OrderScheduleService {

    private final Logger log = LoggerFactory.getLogger(OrderScheduleServiceImpl.class);

    private final OrderScheduleRepository orderScheduleRepository;

    public OrderScheduleServiceImpl(OrderScheduleRepository orderScheduleRepository) {
        this.orderScheduleRepository = orderScheduleRepository;
    }

    /**
     * Save a orderSchedule.
     *
     * @param orderSchedule the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OrderSchedule save(OrderSchedule orderSchedule) {
        log.debug("Request to save OrderSchedule : {}", orderSchedule);
        return orderScheduleRepository.save(orderSchedule);
    }

    /**
     * Get all the orderSchedules.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderSchedule> findAll() {
        log.debug("Request to get all OrderSchedules");
        return orderScheduleRepository.findAll();
    }


    /**
     * Get one orderSchedule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderSchedule> findOne(Long id) {
        log.debug("Request to get OrderSchedule : {}", id);
        return orderScheduleRepository.findById(id);
    }

    /**
     * Delete the orderSchedule by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderSchedule : {}", id);
        orderScheduleRepository.deleteById(id);
    }
}
