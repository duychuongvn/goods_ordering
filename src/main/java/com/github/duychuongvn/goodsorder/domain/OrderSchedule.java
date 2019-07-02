package com.github.duychuongvn.goodsorder.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.github.duychuongvn.goodsorder.domain.enumeration.OrderScheduleStatus;

/**
 * A OrderSchedule.
 */
@Entity
@Table(name = "order_schedule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "open_date")
    private LocalDate openDate;

    @Column(name = "close_date")
    private LocalDate closeDate;

    @Column(name = "expected_packing_date")
    private LocalDate expectedPackingDate;

    @Column(name = "expected_delivery_date")
    private LocalDate expectedDeliveryDate;

    @Column(name = "max_order_number")
    private Integer maxOrderNumber;

    @Column(name = "current_order_number")
    private Integer currentOrderNumber = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderScheduleStatus status = OrderScheduleStatus.OPEN;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "last_updated_at")
    private ZonedDateTime lastUpdatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public OrderSchedule openDate(LocalDate openDate) {
        this.openDate = openDate;
        return this;
    }

    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public OrderSchedule closeDate(LocalDate closeDate) {
        this.closeDate = closeDate;
        return this;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    public LocalDate getExpectedPackingDate() {
        return expectedPackingDate;
    }

    public OrderSchedule expectedPackingDate(LocalDate expectedPackingDate) {
        this.expectedPackingDate = expectedPackingDate;
        return this;
    }

    public void setExpectedPackingDate(LocalDate expectedPackingDate) {
        this.expectedPackingDate = expectedPackingDate;
    }

    public LocalDate getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public OrderSchedule expectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
        return this;
    }

    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public Integer getMaxOrderNumber() {
        return maxOrderNumber;
    }

    public OrderSchedule maxOrderNumber(Integer maxOrderNumber) {
        this.maxOrderNumber = maxOrderNumber;
        return this;
    }

    public void setMaxOrderNumber(Integer maxOrderNumber) {
        this.maxOrderNumber = maxOrderNumber;
    }

    public Integer getCurrentOrderNumber() {
        return currentOrderNumber;
    }

    public OrderSchedule currentOrderNumber(Integer currentOrderNumber) {
        this.currentOrderNumber = currentOrderNumber;
        return this;
    }

    public void setCurrentOrderNumber(Integer currentOrderNumber) {
        this.currentOrderNumber = currentOrderNumber;
    }

    public OrderScheduleStatus getStatus() {
        return status;
    }

    public OrderSchedule status(OrderScheduleStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OrderScheduleStatus status) {
        this.status = status;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public OrderSchedule createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public OrderSchedule lastUpdatedAt(ZonedDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
        return this;
    }

    public void setLastUpdatedAt(ZonedDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public OrderSchedule createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public OrderSchedule lastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderSchedule)) {
            return false;
        }
        return id != null && id.equals(((OrderSchedule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderSchedule{" +
            "id=" + getId() +
            ", openDate='" + getOpenDate() + "'" +
            ", closeDate='" + getCloseDate() + "'" +
            ", expectedPackingDate='" + getExpectedPackingDate() + "'" +
            ", expectedDeliveryDate='" + getExpectedDeliveryDate() + "'" +
            ", maxOrderNumber=" + getMaxOrderNumber() +
            ", currentOrderNumber=" + getCurrentOrderNumber() +
            ", status='" + getStatus() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", lastUpdatedAt='" + getLastUpdatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastUpdatedBy='" + getLastUpdatedBy() + "'" +
            "}";
    }
}
