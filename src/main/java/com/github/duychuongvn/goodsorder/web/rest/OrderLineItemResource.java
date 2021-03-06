package com.github.duychuongvn.goodsorder.web.rest;

import com.github.duychuongvn.goodsorder.config.Constants;
import com.github.duychuongvn.goodsorder.domain.Order;
import com.github.duychuongvn.goodsorder.domain.OrderLineItem;
import com.github.duychuongvn.goodsorder.service.OrderLineItemService;
import com.github.duychuongvn.goodsorder.service.OrderService;
import com.github.duychuongvn.goodsorder.service.UserService;
import com.github.duychuongvn.goodsorder.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.github.duychuongvn.goodsorder.domain.OrderLineItem}.
 */
@RestController
@RequestMapping("/api")
public class OrderLineItemResource {

    private final Logger log = LoggerFactory.getLogger(OrderLineItemResource.class);

    private static final String ENTITY_NAME = "orderLineItem";

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderLineItemService orderLineItemService;

    public OrderLineItemResource(OrderLineItemService orderLineItemService) {
        this.orderLineItemService = orderLineItemService;
    }

    /**
     * {@code POST  /order-line-items} : Create a new orderLineItem.
     *
     * @param orderLineItem the orderLineItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderLineItem, or with status {@code 400 (Bad Request)} if the orderLineItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-line-items")
    public ResponseEntity<Order> createOrderLineItem(@Valid @RequestBody OrderLineItem orderLineItem, HttpSession session) throws URISyntaxException {
        log.debug("REST request to save OrderLineItem : {}", orderLineItem);
        if (orderLineItem.getId() != null) {
            throw new BadRequestAlertException("A new orderLineItem cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Order order = (Order) session.getAttribute(Constants.SESSION_KEY_ORDER);
        order = orderLineItemService.createOrUpdateOrder(order, orderLineItem.getReferenceUrl());
        session.setAttribute(Constants.SESSION_KEY_ORDER, order);
        return ResponseEntity.created(new URI("/api/orders/" + order.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, order.getId().toString()))
            .body(order);
    }

    /**
     * {@code PUT  /order-line-items} : Updates an existing orderLineItem.
     *
     * @param orderLineItem the orderLineItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderLineItem,
     * or with status {@code 400 (Bad Request)} if the orderLineItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderLineItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-line-items")
    public ResponseEntity<OrderLineItem> updateOrderLineItem(@Valid @RequestBody OrderLineItem orderLineItem) throws URISyntaxException {
        log.debug("REST request to update OrderLineItem : {}", orderLineItem);
        if (orderLineItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderLineItem result = orderLineItemService.save(orderLineItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderLineItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-line-items} : get all the orderLineItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderLineItems in body.
     */
    @GetMapping("/order-line-items")
    public List<OrderLineItem> getAllOrderLineItems() {
        log.debug("REST request to get all OrderLineItems");
        return orderLineItemService.findAll();
    }

    /**
     * {@code GET  /order-line-items/:id} : get the "id" orderLineItem.
     *
     * @param id the id of the orderLineItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderLineItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-line-items/{id}")
    public ResponseEntity<OrderLineItem> getOrderLineItem(@PathVariable Long id) {
        log.debug("REST request to get OrderLineItem : {}", id);
        Optional<OrderLineItem> orderLineItem = orderLineItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderLineItem);
    }

    /**
     * {@code DELETE  /order-line-items/:id} : delete the "id" orderLineItem.
     *
     * @param id the id of the orderLineItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-line-items/{id}")
    public ResponseEntity<Void> deleteOrderLineItem(@PathVariable Long id) {
        log.debug("REST request to delete OrderLineItem : {}", id);
        orderLineItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
