package com.github.duychuongvn.goodsorder.service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderLineItemDto {
    private Long id;
    private String size;
    @NotNull
    private Integer quantity = 1;
}
