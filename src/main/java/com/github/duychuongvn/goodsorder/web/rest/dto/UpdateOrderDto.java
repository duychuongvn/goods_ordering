package com.github.duychuongvn.goodsorder.web.rest.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
@Getter
@Setter
public class UpdateOrderDto {
    @Id
    @NotNull
    private Long id;
    private String remark;
    @NotNull
    private String address1;
    private String address2;
    @NotNull
    private String phone1;
    private String phone2;
    private String email;
    private String zipCode;
    @NotNull
    private String city;
    private String district;

}
