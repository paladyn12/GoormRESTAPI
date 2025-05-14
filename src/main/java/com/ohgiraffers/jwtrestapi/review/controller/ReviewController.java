package com.ohgiraffers.jwtrestapi.review.controller;


import com.ohgiraffers.jwtrestapi.common.Criteria;
import com.ohgiraffers.jwtrestapi.common.PageDTO;
import com.ohgiraffers.jwtrestapi.common.PagingResponseDTO;
import com.ohgiraffers.jwtrestapi.common.ResponseDTO;
import com.ohgiraffers.jwtrestapi.review.dto.ReviewDTO;
import com.ohgiraffers.jwtrestapi.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseDTO> insertProductReview(@RequestBody ReviewDTO reviewDTO) {


        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.CREATED, "리뷰 등록 성공", reviewService.insertProductReview(reviewDTO)));
    }

    @Operation(summary = "상품 리뷰 리스트 조회 요청", description = "해당 상품에 등록된 리뷰 리스트 조회가 진행됩니다.", tags = { "ReviewController" })
    @GetMapping("/reviews/{productCode}")
    public ResponseEntity<ResponseDTO> selectReviewListWithPaging(
            @PathVariable String productCode,
            @RequestParam(name = "offset", defaultValue = "1") String offset) {
//        log.info("[ReviewController] selectReviewListWithPaging : " + offset);
//        log.info("[ReviewController] productCode : " + productCode);

        // 1페이지에 10개 데이터라는 검색 조건을 담을 객체
        Criteria cri = new Criteria(Integer.valueOf(offset), 10);
        cri.setSearchValue(productCode); // 상품을 검색 조건으로 설정

        long total = reviewService.selectReviewTotal(Integer.parseInt(cri.getSearchValue()));
        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();

        // DB에서 조회해온 데이터를 담는 setData();
        pagingResponseDTO.setData(reviewService.selectReviewListWithPaging(cri));
        pagingResponseDTO.setPageInfo(new PageDTO(cri, (int) total));

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", pagingResponseDTO));
    }

    @Operation(summary = "리뷰 상세 페이지 조회 요청", description = "해당 리뷰의 상세 페이지 조회가 진행됩니다.", tags = { "ReviewController" })
    @GetMapping("/reviews/product/{reviewCode}")
    public ResponseEntity<ResponseDTO> selectReviewDetail(@PathVariable int reviewCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, reviewCode + " 번 리뷰 상세 조회 성공", reviewService.selectReviewDetail(reviewCode)));
    }

    @Operation(summary = "리뷰 수정 요청", description = "리뷰 작성자의 리뷰 수정이 진행됩니다.", tags = { "ReviewController" })
    @PutMapping("/reviews")
    public ResponseEntity<ResponseDTO> updateProductReview(@RequestBody ReviewDTO reviewDTO) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "리뷰 수정 성공", reviewService.updateProductReview(reviewDTO)));
    }
}