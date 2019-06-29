package com.github.duychuongvn.goodsorder.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.duychuongvn.core.usertype.Blob2List;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.duychuongvn.goodsorder.domain.enumeration.OrderSource;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

/**
 * A OrderLineItem.
 */
@Entity
@Table(name = "order_line_item")
@TypeDef(typeClass = Blob2List.class, name = "serializedList")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderLineItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "reference_url", nullable = false)
    private String referenceUrl;

    @Column(name = "origin_price", precision = 21, scale = 2)
    private BigDecimal originPrice;

    @Column(name = "sale_price", precision = 21, scale = 2)
    private BigDecimal salePrice;

    @Column(name = "tax", precision = 21, scale = 2)
    private BigDecimal tax;

    @Column(name = "goods_name")
    private String goodsName;

    @Column(name = "goods_id")
    private String goodsId;

    @Column(name = "goods_sku")
    private String goodsSKU;

    @Column(name = "jhi_size")
    private String size;

    @Column(name = "remark")
    private String remark;

    @Lob
    @Column(name = "images")
    @Type(type = "serializedList")
    private List<String> images = new ArrayList<>();

    @Column(name = "images_content_type")
    private String imagesContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "source")
    private OrderSource source;

    @ManyToOne
    @JsonIgnoreProperties("orderLineItems")
    private Order order;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferenceUrl() {
        return referenceUrl;
    }

    public OrderLineItem referenceUrl(String referenceUrl) {
        this.referenceUrl = referenceUrl;
        return this;
    }

    public void setReferenceUrl(String referenceUrl) {
        this.referenceUrl = referenceUrl;
    }

    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    public OrderLineItem originPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
        return this;
    }

    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public OrderLineItem salePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
        return this;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public OrderLineItem tax(BigDecimal tax) {
        this.tax = tax;
        return this;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public OrderLineItem goodsName(String goodsName) {
        this.goodsName = goodsName;
        return this;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public OrderLineItem goodsId(String goodsId) {
        this.goodsId = goodsId;
        return this;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsSKU() {
        return goodsSKU;
    }

    public OrderLineItem goodsSKU(String goodsSKU) {
        this.goodsSKU = goodsSKU;
        return this;
    }

    public void setGoodsSKU(String goodsSKU) {
        this.goodsSKU = goodsSKU;
    }

    public String getSize() {
        return size;
    }

    public OrderLineItem size(String size) {
        this.size = size;
        return this;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getRemark() {
        return remark;
    }

    public OrderLineItem remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getImages() {
        return images;
    }

    public OrderLineItem images(List<String> images) {
        this.images = images;
        return this;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getImagesContentType() {
        return imagesContentType;
    }

    public OrderLineItem imagesContentType(String imagesContentType) {
        this.imagesContentType = imagesContentType;
        return this;
    }

    public void setImagesContentType(String imagesContentType) {
        this.imagesContentType = imagesContentType;
    }

    public OrderSource getSource() {
        return source;
    }

    public OrderLineItem source(OrderSource source) {
        this.source = source;
        return this;
    }

    public void setSource(OrderSource source) {
        this.source = source;
    }

    public Order getOrder() {
        return order;
    }

    public OrderLineItem order(Order order) {
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
        if (!(o instanceof OrderLineItem)) {
            return false;
        }
        return id != null && id.equals(((OrderLineItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderLineItem{" +
            "id=" + getId() +
            ", referenceUrl='" + getReferenceUrl() + "'" +
            ", originPrice=" + getOriginPrice() +
            ", salePrice=" + getSalePrice() +
            ", tax=" + getTax() +
            ", goodsName='" + getGoodsName() + "'" +
            ", goodsId='" + getGoodsId() + "'" +
            ", goodsSKU='" + getGoodsSKU() + "'" +
            ", size='" + getSize() + "'" +
            ", remark='" + getRemark() + "'" +
            ", images='" + getImages() + "'" +
            ", imagesContentType='" + getImagesContentType() + "'" +
            ", source='" + getSource() + "'" +
            "}";
    }
}
