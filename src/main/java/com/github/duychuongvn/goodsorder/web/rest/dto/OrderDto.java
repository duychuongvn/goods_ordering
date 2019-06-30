package com.github.duychuongvn.goodsorder.web.rest.dto;

import com.github.duychuongvn.goodsorder.domain.OrderLineItem;
import com.github.duychuongvn.goodsorder.domain.User;
import com.github.duychuongvn.goodsorder.domain.enumeration.DeliveryStatus;
import com.github.duychuongvn.goodsorder.domain.enumeration.OrderStatus;
import com.github.duychuongvn.goodsorder.domain.enumeration.PaymentStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderDto  {
    private Long id;
    @Singular
    private List<OrderLineItem> items = new ArrayList<>();
    private String exchangeDate;
    private BigDecimal exchangeRate;
    private BigDecimal totalPay;// total price in JPY
    private BigDecimal totalPayInVnd; // total price in VND
    private BigDecimal orderFeeInVnd; // total delivery fee in VND
    private LocalDateTime estimatedDeliveryDate; // date to delivery products
    private LocalDateTime estimatedPackingDate; // date to buy products
    private OrderStatus orderStatus;
    private DeliveryStatus deliveryStatus;
    private PaymentStatus paymentStatus;
    private User buyer;

}
