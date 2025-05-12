package com.ohgiraffers.jwtrestapi.review.controller;


import com.ohgiraffers.jwtrestapi.common.ResponseDTO;
import com.ohgiraffers.jwtrestapi.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(summary = "상품 리뷰 등록 요청", description = "해당 상품 리뷰 등록이 진행됩니다.", tags = { "ReviewController" })
    @PostMapping("/reviews")
    public ResponseEntity<ResponseDTO> insertProductReview() {

        return null;
    }

    @Operation(summary = "상품 리뷰 리스트 조회 요청", description = "해당 상품에 등록된 리뷰 리스트 조회가 진행됩니다.", tags = { "ReviewController" })
    @GetMapping("/reviews/{productCode}")
    public ResponseEntity<ResponseDTO> selectReviewListWithPaging() {
//        log.info("[ReviewController] selectReviewListWithPaging : " + offset);
//        log.info("[ReviewController] productCode : " + productCode);


        return null;
    }

    @Operation(summary = "리뷰 상세 페이지 조회 요청", description = "해당 리뷰의 상세 페이지 조회가 진행됩니다.", tags = { "ReviewController" })
    @GetMapping("/reviews/product/{reviewCode}")
    public ResponseEntity<ResponseDTO> selectReviewDetail() {

        return null;
    }

    @Operation(summary = "리뷰 수정 요청", description = "리뷰 작성자의 리뷰 수정이 진행됩니다.", tags = { "ReviewController" })
    @PutMapping("/reviews")
    public ResponseEntity<ResponseDTO> updateProductReview() {

        return null;
    }
}