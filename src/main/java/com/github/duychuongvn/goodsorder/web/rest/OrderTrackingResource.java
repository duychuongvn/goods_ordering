package com.github.duychuongvn.goodsorder.web.rest;

import com.github.duychuongvn.goodsorder.domain.OrderTracking;
import com.github.duychuongvn.goodsorder.service.OrderTrackingService;
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
 * REST controller for managing {@link com.github.duychuongvn.goodsorder.domain.OrderTracking}.
 */
@RestController
@RequestMapping("/api")
public class OrderTrackingResource {

    private final Logger log = LoggerFactory.getLogger(OrderTrackingResource.class);

    private static final String ENTITY_NAME = "orderTracking";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderTrackingService orderTrackingService;

    public OrderTrackingResource(OrderTrackingService orderTrackingService) {
        this.orderTrackingService = orderTrackingService;
    }

    /**
     * {@code POST  /order-trackings} : Create a new orderTracking.
     *
     * @param orderTracking the orderTracking to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderTracking, or with status {@code 400 (Bad Request)} if the orderTracking has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-trackings")
    public ResponseEntity<OrderTracking> createOrderTracking(@RequestBody OrderTracking orderTracking) throws URISyntaxException {
        log.debug("REST request to save OrderTracking : {}", orderTracking);
        if (orderTracking.getId() != null) {
            throw new BadRequestAlertException("A new orderTracking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderTracking result = orderTrackingService.save(orderTracking);
        return ResponseEntity.created(new URI("/api/order-trackings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-trackings} : Updates an existing orderTracking.
     *
     * @param orderTracking the orderTracking to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderTracking,
     * or with status {@code 400 (Bad Request)} if the orderTracking is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderTracking couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-trackings")
    public ResponseEntity<OrderTracking> updateOrderTracking(@RequestBody OrderTracking orderTracking) throws URISyntaxException {
        log.debug("REST request to update OrderTracking : {}", orderTracking);
        if (orderTracking.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderTracking result = orderTrackingService.save(orderTracking);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderTracking.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-trackings} : get all the orderTrackings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderTrackings in body.
     */
    @GetMapping("/order-trackings")
    public List<OrderTracking> getAllOrderTrackings() {
        log.debug("REST request to get all OrderTrackings");
        return orderTrackingService.findAll();
    }

    /**
     * {@code GET  /order-trackings/:id} : get the "id" orderTracking.
     *
     * @param id the id of the orderTracking to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderTracking, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-trackings/{id}")
    public ResponseEntity<OrderTracking> getOrderTracking(@PathVariable Long id) {
        log.debug("REST request to get OrderTracking : {}", id);
        Optional<OrderTracking> orderTracking = orderTrackingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderTracking);
    }

    /**
     * {@code DELETE  /order-trackings/:id} : delete the "id" orderTracking.
     *
     * @param id the id of the orderTracking to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-trackings/{id}")
    public ResponseEntity<Void> deleteOrderTracking(@PathVariable Long id) {
        log.debug("REST request to delete OrderTracking : {}", id);
        orderTrackingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
