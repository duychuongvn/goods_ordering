package com.github.duychuongvn.goodsorder.web.rest;

import com.github.duychuongvn.goodsorder.domain.OrderSchedule;
import com.github.duychuongvn.goodsorder.service.OrderScheduleService;
import com.github.duychuongvn.goodsorder.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.github.duychuongvn.goodsorder.domain.OrderSchedule}.
 */
@RestController
@RequestMapping("/api")
public class OrderScheduleResource {

    private final Logger log = LoggerFactory.getLogger(OrderScheduleResource.class);

    private static final String ENTITY_NAME = "orderSchedule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderScheduleService orderScheduleService;

    public OrderScheduleResource(OrderScheduleService orderScheduleService) {
        this.orderScheduleService = orderScheduleService;
    }

    /**
     * {@code POST  /order-schedules} : Create a new orderSchedule.
     *
     * @param orderSchedule the orderSchedule to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderSchedule, or with status {@code 400 (Bad Request)} if the orderSchedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-schedules")
    public ResponseEntity<OrderSchedule> createOrderSchedule(@RequestBody OrderSchedule orderSchedule) throws URISyntaxException {
        log.debug("REST request to save OrderSchedule : {}", orderSchedule);
        if (orderSchedule.getId() != null) {
            throw new BadRequestAlertException("A new orderSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderSchedule result = orderScheduleService.save(orderSchedule);
        return ResponseEntity.created(new URI("/api/order-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-schedules} : Updates an existing orderSchedule.
     *
     * @param orderSchedule the orderSchedule to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderSchedule,
     * or with status {@code 400 (Bad Request)} if the orderSchedule is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderSchedule couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-schedules")
    public ResponseEntity<OrderSchedule> updateOrderSchedule(@RequestBody OrderSchedule orderSchedule) throws URISyntaxException {
        log.debug("REST request to update OrderSchedule : {}", orderSchedule);
        if (orderSchedule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderSchedule result = orderScheduleService.save(orderSchedule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderSchedule.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-schedules} : get all the orderSchedules.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderSchedules in body.
     */
    @GetMapping("/order-schedules")
    public List<OrderSchedule> getAllOrderSchedules() {
        log.debug("REST request to get all OrderSchedules");
        return orderScheduleService.findAll();
    }

    /**
     * {@code GET  /order-schedules/:id} : get the "id" orderSchedule.
     *
     * @param id the id of the orderSchedule to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderSchedule, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-schedules/{id}")
    public ResponseEntity<OrderSchedule> getOrderSchedule(@PathVariable Long id) {
        log.debug("REST request to get OrderSchedule : {}", id);
        Optional<OrderSchedule> orderSchedule = orderScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderSchedule);
    }

    /**
     * {@code DELETE  /order-schedules/:id} : delete the "id" orderSchedule.
     *
     * @param id the id of the orderSchedule to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-schedules/{id}")
    public ResponseEntity<Void> deleteOrderSchedule(@PathVariable Long id) {
        log.debug("REST request to delete OrderSchedule : {}", id);
        orderScheduleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
