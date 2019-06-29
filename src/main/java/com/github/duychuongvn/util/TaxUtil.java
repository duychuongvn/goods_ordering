package com.github.duychuongvn.util;

import com.github.duychuongvn.goodsorder.domain.enumeration.OrderSource;

import java.math.BigDecimal;

public class TaxUtil {

    public static BigDecimal getTax(OrderSource orderSource, BigDecimal price) {
        if(orderSource == OrderSource.AEO_JP) {
            return price.multiply(BigDecimal.valueOf(0.08));
        }
        return BigDecimal.ZERO;
    }
}
