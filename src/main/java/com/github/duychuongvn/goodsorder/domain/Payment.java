package com.github.duychuongvn.goodsorder.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.github.duychuongvn.goodsorder.domain.enumeration.PaymentStatus;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_code")
    private String paymentCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "received_amount", precision = 21, scale = 2)
    private BigDecimal receivedAmount;

    @Column(name = "paid_time")
    private ZonedDateTime paidTime;

    @Column(name = "bank_account")
    private String bankAccount;

    @Column(name = "bank_account_holder")
    private String bankAccountHolder;

    @Column(name = "bank_info")
    private String bankInfo;

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
    private Order order;

    @OneToMany(mappedBy = "payment")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Transaction> transactions = new HashSet<>();

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

    public Payment paymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
        return this;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public Payment paymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getReceivedAmount() {
        return receivedAmount;
    }

    public Payment receivedAmount(BigDecimal receivedAmount) {
        this.receivedAmount = receivedAmount;
        return this;
    }

    public void setReceivedAmount(BigDecimal receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public ZonedDateTime getPaidTime() {
        return paidTime;
    }

    public Payment paidTime(ZonedDateTime paidTime) {
        this.paidTime = paidTime;
        return this;
    }

    public void setPaidTime(ZonedDateTime paidTime) {
        this.paidTime = paidTime;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public Payment bankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
        return this;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankAccountHolder() {
        return bankAccountHolder;
    }

    public Payment bankAccountHolder(String bankAccountHolder) {
        this.bankAccountHolder = bankAccountHolder;
        return this;
    }

    public void setBankAccountHolder(String bankAccountHolder) {
        this.bankAccountHolder = bankAccountHolder;
    }

    public String getBankInfo() {
        return bankInfo;
    }

    public Payment bankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
        return this;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public Payment createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public Payment lastUpdatedAt(ZonedDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
        return this;
    }

    public void setLastUpdatedAt(ZonedDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Payment createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public Payment lastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Order getOrder() {
        return order;
    }

    public Payment order(Order order) {
        this.order = order;
        return this;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public Payment transactions(Set<Transaction> transactions) {
        this.transactions = transactions;
        return this;
    }

    public Payment addTransactions(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setPayment(this);
        return this;
    }

    public Payment removeTransactions(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.setPayment(null);
        return this;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        return id != null && id.equals(((Payment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", paymentCode='" + getPaymentCode() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", receivedAmount=" + getReceivedAmount() +
            ", paidTime='" + getPaidTime() + "'" +
            ", bankAccount='" + getBankAccount() + "'" +
            ", bankAccountHolder='" + getBankAccountHolder() + "'" +
            ", bankInfo='" + getBankInfo() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", lastUpdatedAt='" + getLastUpdatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastUpdatedBy='" + getLastUpdatedBy() + "'" +
            "}";
    }
}
