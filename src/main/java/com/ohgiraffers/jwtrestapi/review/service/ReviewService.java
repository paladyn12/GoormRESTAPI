package com.ohgiraffers.jwtrestapi.review.service;

import com.ohgiraffers.jwtrestapi.common.Criteria;
import com.ohgiraffers.jwtrestapi.review.dto.ReviewAndMemberDTO;
import com.ohgiraffers.jwtrestapi.review.dto.ReviewDTO;
import com.ohgiraffers.jwtrestapi.review.entity.Review;
import com.ohgiraffers.jwtrestapi.review.entity.ReviewAndMember;
import com.ohgiraffers.jwtrestapi.review.repository.ReviewAndMemberRepository;
import com.ohgiraffers.jwtrestapi.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ModelMapper modelMapper;
    private final ReviewRepository reviewRepository;
    private final ReviewAndMemberRepository reviewAndMemberRepository;

    @Transactional
    public String insertProductReview(ReviewDTO reviewDTO) {
        log.info("[ReviewService] insertProductReview() Start");

        int result = 0;
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        String reviewDate = sdf.format(now);

        reviewDTO.setReviewCreateDate(reviewDate);
        try {
            Review review = modelMapper.map(reviewDTO, Review.class);
            reviewRepository.save(review);
            result = 1;

        } catch (Exception e) {

            log.error("[Review] 등록 중 에러 발생 : {}", e);

        }

        log.info("[ReviewService] insertProductReview() End");

        return (result > 0) ? "리뷰 등록 성공" : "리뷰 등록 실패";
    }

    public long selectReviewTotal(int productCode) {
        log.info("[ReviewService] selectReviewTotal() Start");

        long result = reviewRepository.countByProductCode(productCode);

        log.info("[ReviewService] selectReviewTotal() End");

        return result;
    }

    public List<ReviewAndMemberDTO> selectReviewListWithPaging(Criteria cri) {
        log.info("[ReviewService] selectReviewListWithPaging() Start");

        int index = cri.getPageNum() - 1;
        int count = cri.getAmount();

        Pageable pageable = PageRequest.of(index, count, Sort.by("reviewCode"));

        // 리뷰 조회 시 작성 회원 정보가 필요하기 때문에 Member와 연관관계 매핑
        Page<ReviewAndMember> result = reviewAndMemberRepository.findByProductCode(Integer.valueOf(cri.getSearchValue()), pageable);
        List<ReviewAndMember> reviewList = result.getContent();

        log.info("[ReviewService] selectReviewListWithPaging() End");

        return reviewList.stream().map(review -> modelMapper.map(review, ReviewAndMemberDTO.class))
                .collect(Collectors.toList());
    }

    public ReviewAndMemberDTO selectReviewDetail(int reviewCode) {
        log.info("[ReviewService] getReviewDetail() Start");

        ReviewAndMember review = reviewAndMemberRepository.findByReviewCode(reviewCode).get();

        log.info("[ReviewService] getReviewDetail() End");

        return modelMapper.map(review, ReviewAndMemberDTO.class);
    }

    @Transactional
    public String updateProductReview(ReviewDTO reviewDTO) {
        log.info("[ReviewService] updateProductReview() Start");

        int result = 0;

        try {
            Review review = reviewRepository.findById(reviewDTO.getReviewCode()).get();
            review = review.toBuilder()
                    .reviewTitle(reviewDTO.getReviewTitle())
                    .reviewContent(reviewDTO.getReviewContent())
                    .build();
            reviewRepository.save(review);

            result = 1;

        } catch (Exception e) {
            log.error("[Review] 수정 중 에러 발생 : {}", e);
        }

        log.info("[ReviewService] updateProductReview() End");

        return (result > 0) ? "수정 성공" : "수정 실패";
    }



}