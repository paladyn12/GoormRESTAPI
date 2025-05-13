package com.ohgiraffers.jwtrestapi.purchase.service;

import com.ohgiraffers.jwtrestapi.member.repository.MemberRepository;
import com.ohgiraffers.jwtrestapi.product.entity.Product;
import com.ohgiraffers.jwtrestapi.product.repository.ProductRepository;
import com.ohgiraffers.jwtrestapi.purchase.dto.OrderAndProductDTO;
import com.ohgiraffers.jwtrestapi.purchase.dto.PurchaseDTO;
import com.ohgiraffers.jwtrestapi.purchase.entity.Order;
import com.ohgiraffers.jwtrestapi.purchase.repository.OrderAndProductRepository;
import com.ohgiraffers.jwtrestapi.purchase.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderAndProductRepository orderAndProductRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public String insertProduct(PurchaseDTO purchaseDTO) {
        log.info("[OrderService] insertPurchase() Start");

        int result = 0;

        try {

            // 1. 해당 주문을 진행하고 있는 회원 PK 값 조회
            int memberCode = memberRepository.findMemberCodeByMemberId(purchaseDTO.getMemberId());

            // 2. 주문 INSERT
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
            String orderDate = sdf.format(now);

            Order order = Order.builder()
                    .productCode(purchaseDTO.getProductCode())
                    .orderMember(memberCode)
                    .orderPhone(purchaseDTO.getOrderPhone())
                    .orderAddress(purchaseDTO.getOrderAddress())
                    .orderDate(orderDate)
                    .orderEmail(purchaseDTO.getOrderEmail())
                    .orderReceiver(purchaseDTO.getOrderReceiver())
                    .orderAmount(String.valueOf(purchaseDTO.getOrderAmount()))
                    .build();

            orderRepository.save(order);

            // 3. 상품(Product) 재고 Update
            // 상품 한 행 식별
            Product product = productRepository.findById(Integer.valueOf(order.getProductCode())).get();

            product = product.toBuilder()
                    .productStock(product.getProductStock() - purchaseDTO.getOrderAmount())
                    .build();
            productRepository.save(product);

            result = 1;
        } catch (Exception e) {
            log.error("[Order] Exception");
        }
        log.info("[OrderService] insertPurchase() End");
        return (result > 0) ? "주문 성공" : "주문 실패";
    }

    public List<OrderAndProductDTO> selectPurchaseList(String memberId) {
        log.info("[OrderService] selectPurchaseList() Start");

        int memberCode = memberRepository.findMemberCodeByMemberId(memberId);
        List<OrderAndProductRepository> orderList = orderAndProductRepository.findByOrderMember(memberCode);

        log.info("[OrderService] selectPurchaseList() End");

        return orderList.stream().map(
                order -> modelMapper.map(order, OrderAndProductDTO.class)
        ).collect(Collectors.toList());
    }

}