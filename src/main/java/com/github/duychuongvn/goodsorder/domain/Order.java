package com.github.duychuongvn.goodsorder.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    @NotNull
    @Column(name = "payment_code", nullable = false)
    private String paymentCode;

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

    @Column(name = "exchange_rate", precision = 21, scale = 2)
    private BigDecimal exchangeRate;

    @Column(name = "total_jpy_price", precision = 21, scale = 2)
    private BigDecimal totalJpyPrice;

    @Column(name = "delivery_fee_vnd", precision = 21, scale = 2)
    private BigDecimal deliveryFeeVnd;

    @Column(name = "total_pay_vnd", precision = 21, scale = 2)
    private BigDecimal totalPayVnd;

    @Column(name = "deposited_vnd", precision = 21, scale = 2)
    private BigDecimal depositedVnd;

    @Column(name = "paid_vnd", precision = 21, scale = 2)
    private BigDecimal paidVnd;

    @Column(name = "packing_date")
    private LocalDate packingDate;

    @Column(name = "estimated_deliver_date")
    private LocalDate estimatedDeliverDate;

    @Column(name = "delivered_date")
    private LocalDate deliveredDate;

    @Column(name = "finish_payment_time")
    private ZonedDateTime finishPaymentTime;

    @Column(name = "remark")
    private String remark;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "phone_1")
    private String phone1;

    @Column(name = "phone_2")
    private String phone2;

    @Column(name = "email")
    private String email;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "last_updated_at")
    private ZonedDateTime lastUpdatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @OneToOne
    @JoinColumn(unique = true)
    private Payment payment;

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

    public String getPaymentCode() {
        return paymentCode;
    }

    public Order paymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
        return this;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
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

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public Order exchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
        return this;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getTotalJpyPrice() {
        return totalJpyPrice;
    }

    public Order totalJpyPrice(BigDecimal totalJpyPrice) {
        this.totalJpyPrice = totalJpyPrice;
        return this;
    }

    public void setTotalJpyPrice(BigDecimal totalJpyPrice) {
        this.totalJpyPrice = totalJpyPrice;
    }

    public BigDecimal getDeliveryFeeVnd() {
        return deliveryFeeVnd;
    }

    public Order deliveryFeeVnd(BigDecimal deliveryFeeVnd) {
        this.deliveryFeeVnd = deliveryFeeVnd;
        return this;
    }

    public void setDeliveryFeeVnd(BigDecimal deliveryFeeVnd) {
        this.deliveryFeeVnd = deliveryFeeVnd;
    }

    public BigDecimal getTotalPayVnd() {
        return totalPayVnd;
    }

    public Order totalPayVnd(BigDecimal totalPayVnd) {
        this.totalPayVnd = totalPayVnd;
        return this;
    }

    public void setTotalPayVnd(BigDecimal totalPayVnd) {
        this.totalPayVnd = totalPayVnd;
    }

    public BigDecimal getDepositedVnd() {
        return depositedVnd;
    }

    public Order depositedVnd(BigDecimal depositedVnd) {
        this.depositedVnd = depositedVnd;
        return this;
    }

    public void setDepositedVnd(BigDecimal depositedVnd) {
        this.depositedVnd = depositedVnd;
    }

    public BigDecimal getPaidVnd() {
        return paidVnd;
    }

    public Order paidVnd(BigDecimal paidVnd) {
        this.paidVnd = paidVnd;
        return this;
    }

    public void setPaidVnd(BigDecimal paidVnd) {
        this.paidVnd = paidVnd;
    }

    public LocalDate getPackingDate() {
        return packingDate;
    }

    public Order packingDate(LocalDate packingDate) {
        this.packingDate = packingDate;
        return this;
    }

    public void setPackingDate(LocalDate packingDate) {
        this.packingDate = packingDate;
    }

    public LocalDate getEstimatedDeliverDate() {
        return estimatedDeliverDate;
    }

    public Order estimatedDeliverDate(LocalDate estimatedDeliverDate) {
        this.estimatedDeliverDate = estimatedDeliverDate;
        return this;
    }

    public void setEstimatedDeliverDate(LocalDate estimatedDeliverDate) {
        this.estimatedDeliverDate = estimatedDeliverDate;
    }

    public LocalDate getDeliveredDate() {
        return deliveredDate;
    }

    public Order deliveredDate(LocalDate deliveredDate) {
        this.deliveredDate = deliveredDate;
        return this;
    }

    public void setDeliveredDate(LocalDate deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public ZonedDateTime getFinishPaymentTime() {
        return finishPaymentTime;
    }

    public Order finishPaymentTime(ZonedDateTime finishPaymentTime) {
        this.finishPaymentTime = finishPaymentTime;
        return this;
    }

    public void setFinishPaymentTime(ZonedDateTime finishPaymentTime) {
        this.finishPaymentTime = finishPaymentTime;
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

    public String getAddress1() {
        return address1;
    }

    public Order address1(String address1) {
        this.address1 = address1;
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public Order address2(String address2) {
        this.address2 = address2;
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPhone1() {
        return phone1;
    }

    public Order phone1(String phone1) {
        this.phone1 = phone1;
        return this;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public Order phone2(String phone2) {
        this.phone2 = phone2;
        return this;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail() {
        return email;
    }

    public Order email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Order zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public Order city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public Order district(String district) {
        this.district = district;
        return this;
    }

    public void setDistrict(String district) {
        this.district = district;
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

    public Payment getPayment() {
        return payment;
    }

    public Order payment(Payment payment) {
        this.payment = payment;
        return this;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
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
            ", paymentCode='" + getPaymentCode() + "'" +
            ", orderDate='" + getOrderDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", deliveryStatus='" + getDeliveryStatus() + "'" +
            ", exchangeRateId='" + getExchangeRateId() + "'" +
            ", exchangeRate=" + getExchangeRate() +
            ", totalJpyPrice=" + getTotalJpyPrice() +
            ", deliveryFeeVnd=" + getDeliveryFeeVnd() +
            ", totalPayVnd=" + getTotalPayVnd() +
            ", depositedVnd=" + getDepositedVnd() +
            ", paidVnd=" + getPaidVnd() +
            ", packingDate='" + getPackingDate() + "'" +
            ", estimatedDeliverDate='" + getEstimatedDeliverDate() + "'" +
            ", deliveredDate='" + getDeliveredDate() + "'" +
            ", finishPaymentTime='" + getFinishPaymentTime() + "'" +
            ", remark='" + getRemark() + "'" +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", phone1='" + getPhone1() + "'" +
            ", phone2='" + getPhone2() + "'" +
            ", email='" + getEmail() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", city='" + getCity() + "'" +
            ", district='" + getDistrict() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", lastUpdatedAt='" + getLastUpdatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastUpdatedBy='" + getLastUpdatedBy() + "'" +
            "}";
    }
}
