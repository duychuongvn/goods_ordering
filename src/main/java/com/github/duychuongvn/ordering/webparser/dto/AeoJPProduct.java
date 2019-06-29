package com.github.duychuongvn.ordering.webparser.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AeoJPProduct extends OriginProduct {

    private String sku;

    @Override
    public String toString() {
        return "AeoJPProduct{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", shortName='" + getShortName() + '\'' +
                ", referenceUrl='" + getReferenceUrl() + '\'' +
                ", originPrice=" + getOriginPrice() +
                ", salePrice=" + getSalePrice() +
                ", source='" + getSource() + '\'' +
                ", images=" + getImages() +
                "sku='" + sku + '\'' +
                '}';
    }
}
