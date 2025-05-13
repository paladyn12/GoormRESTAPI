package com.ohgiraffers.jwtrestapi.purchase.dto;

import lombok.Data;

@Data
public class OrderDTO {

    private int orderCode;
    private int productCode;
    private int orderMember;
    private String orderPhone;
    private String orderEmail;
    private String orderReceiver;
    private String orderAmount;
    private String orderDate;

}
