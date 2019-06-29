package com.github.duychuongvn.goodsorder.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.github.duychuongvn.goodsorder.domain.enumeration.DeliveryStatus;

/**
 * A OrderTracking.
 */
@Entity
@Table(name = "order_tracking")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderTracking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status")
    private DeliveryStatus deliveryStatus;

    @Column(name = "date_time")
    private ZonedDateTime dateTime;

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

    @ManyToOne
    @JsonIgnoreProperties("orderTrackings")
    private Order order;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public OrderTracking deliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
        return this;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public OrderTracking dateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getRemark() {
        return remark;
    }

    public OrderTracking remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public OrderTracking createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public OrderTracking lastUpdatedAt(ZonedDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
        return this;
    }

    public void setLastUpdatedAt(ZonedDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public OrderTracking createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public OrderTracking lastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Order getOrder() {
        return order;
    }

    public OrderTracking order(Order order) {
        this.order = order;
        return this;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderTracking)) {
            return false;
        }
        return id != null && id.equals(((OrderTracking) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderTracking{" +
            "id=" + getId() +
            ", deliveryStatus='" + getDeliveryStatus() + "'" +
            ", dateTime='" + getDateTime() + "'" +
            ", remark='" + getRemark() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", lastUpdatedAt='" + getLastUpdatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastUpdatedBy='" + getLastUpdatedBy() + "'" +
            "}";
    }
}
