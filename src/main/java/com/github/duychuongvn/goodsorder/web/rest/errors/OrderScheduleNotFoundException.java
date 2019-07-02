package com.github.duychuongvn.goodsorder.web.rest.errors;

public class OrderScheduleNotFoundException extends BadRequestAlertException {

    public OrderScheduleNotFoundException() {
        super(ErrorConstants.ORDER_NOT_READY_TYPE, "Order time is closed", "order", "orderNotReady");
    }
}
