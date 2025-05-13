package com.ohgiraffers.jwtrestapi.purchase.dto;

import com.ohgiraffers.jwtrestapi.product.entity.Product;
import lombok.Data;

@Data
public class OrderAndProductDTO {

    private int orderCode;
    private Product product;
    private int orderMember;
    private String orderPhone;
    private String orderEmail;
    private String orderAddress;
    private String orderReceiver;
    private String orderAmount;
    private String orderDate;
}
