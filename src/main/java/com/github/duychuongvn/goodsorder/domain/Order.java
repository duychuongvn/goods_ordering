package com.github.duychuongvn.goodsorder.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.github.duychuongvn.goodsorder.domain.enumeration.OrderStatus;

import com.github.duychuongvn.goodsorder.domain.enumeration.DeliveryStatus;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date")
    private ZonedDateTime orderDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status")
    private DeliveryStatus deliveryStatus;

    @NotNull
    @Column(name = "exchange_rate_id", nullable = false)
    private String exchangeRateId;

    @Column(name = "remark")
    private String remark;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "last_updated_at")
    private ZonedDateTime lastUpdatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @OneToMany(mappedBy = "order")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderLineItem> orderLineItems = new HashSet<>();

    @OneToMany(mappedBy = "order")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OrderTracking> orderTrackings = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private User merchant;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public Order orderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Order status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public Order deliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
        return this;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getExchangeRateId() {
        return exchangeRateId;
    }

    public Order exchangeRateId(String exchangeRateId) {
        this.exchangeRateId = exchangeRateId;
        return this;
    }

    public void setExchangeRateId(String exchangeRateId) {
        this.exchangeRateId = exchangeRateId;
    }

    public String getRemark() {
        return remark;
    }

    public Order remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public Order createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public Order lastUpdatedAt(ZonedDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
        return this;
    }

    public void setLastUpdatedAt(ZonedDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Order createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public Order lastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Set<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public Order orderLineItems(Set<OrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
        return this;
    }

    public Order addOrderLineItems(OrderLineItem orderLineItem) {
        this.orderLineItems.add(orderLineItem);
        orderLineItem.setOrder(this);
        return this;
    }

    public Order removeOrderLineItems(OrderLineItem orderLineItem) {
        this.orderLineItems.remove(orderLineItem);
        orderLineItem.setOrder(null);
        return this;
    }

    public void setOrderLineItems(Set<OrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public Set<OrderTracking> getOrderTrackings() {
        return orderTrackings;
    }

    public Order orderTrackings(Set<OrderTracking> orderTrackings) {
        this.orderTrackings = orderTrackings;
        return this;
    }

    public Order addOrderTrackings(OrderTracking orderTracking) {
        this.orderTrackings.add(orderTracking);
        orderTracking.setOrder(this);
        return this;
    }

    public Order removeOrderTrackings(OrderTracking orderTracking) {
        this.orderTrackings.remove(orderTracking);
        orderTracking.setOrder(null);
        return this;
    }

    public void setOrderTrackings(Set<OrderTracking> orderTrackings) {
        this.orderTrackings = orderTrackings;
    }

    public User getUser() {
        return user;
    }

    public Order user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getMerchant() {
        return merchant;
    }

    public Order merchant(User user) {
        this.merchant = user;
        return this;
    }

    public void setMerchant(User user) {
        this.merchant = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", deliveryStatus='" + getDeliveryStatus() + "'" +
            ", exchangeRateId='" + getExchangeRateId() + "'" +
            ", remark='" + getRemark() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", lastUpdatedAt='" + getLastUpdatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastUpdatedBy='" + getLastUpdatedBy() + "'" +
            "}";
    }
}
