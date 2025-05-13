package com.ohgiraffers.jwtrestapi.purchase.dto;

import lombok.Data;

@Data
public class PurchaseDTO {
    // 구매 시 사용할 DTO
    private String memberId;
    private String orderAddress;
    private int orderAmount;
    private String orderEmail;
    private String orderPhone;
    private String orderReceiver;
    private int productCode;
}
