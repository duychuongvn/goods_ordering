package com.github.duychuongvn.ordering.webparser.dto;

import com.github.duychuongvn.goodsorder.domain.enumeration.OrderSource;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OriginProduct {

    private String id;
    private String name;
    private String shortName;
    private String referenceUrl;
    @Builder.Default
    private BigDecimal originPrice  = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal salePrice = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal tax = BigDecimal.ZERO;
    private OrderSource source;
    @Singular
    private List<String> images = new ArrayList<>();

    @Override
    public String toString() {
        return "OriginProduct{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", referenceUrl='" + referenceUrl + '\'' +
                ", originPrice=" + originPrice +
                ", salePrice=" + salePrice +
                ", source='" + source + '\'' +
                ", images=" + images +
                '}';
    }
}
