package com.ohgiraffers.jwtrestapi.review.service;

import com.ohgiraffers.jwtrestapi.common.Criteria;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReviewService {


    @Transactional
    public Object insertProductReview() {
        log.info("[ReviewService] insertProductReview() Start");


        log.info("[ReviewService] insertProductReview() End");

        return "" ;
    }

    public long selectReviewTotal(int productCode) {
        log.info("[ReviewService] selectReviewTotal() Start");

        log.info("[ReviewService] selectReviewTotal() End");

        return 0;
    }

    public Object selectReviewListWithPaging(Criteria cri) {
        log.info("[ReviewService] selectReviewListWithPaging() Start");


        log.info("[ReviewService] selectReviewListWithPaging() End");

        return null;
    }

    public Object selectReviewDetail(int reviewCode) {
        log.info("[ReviewService] getReviewDetail() Start");


        log.info("[ReviewService] getReviewDetail() End");

        return null;
    }

    @Transactional
    public Object updateProductReview() {
        log.info("[ReviewService] updateProductReview() Start");


        log.info("[ReviewService] updateProductReview() End");

        return "" ;
    }



}