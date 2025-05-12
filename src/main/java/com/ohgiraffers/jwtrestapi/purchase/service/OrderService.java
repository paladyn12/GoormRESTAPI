package com.ohgiraffers.jwtrestapi.purchase.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {


    @Transactional
    public Object insertProduct() {
        log.info("[OrderService] insertPurchase() Start");
//		log.info("[OrderService] purchaseDTO : {}", purchaseDTO);

        log.info("[OrderService] insertPurchase() End");
        return "";
    }

    public Object selectPurchaseList() {
        log.info("[OrderService] selectPurchaseList() Start");


//        log.info("[OrderService] purchaseList {}", orderList);

        log.info("[OrderService] selectPurchaseList() End");

        return null;
    }

}