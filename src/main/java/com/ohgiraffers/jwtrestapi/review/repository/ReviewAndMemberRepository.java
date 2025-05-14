package com.ohgiraffers.jwtrestapi.review.repository;

import com.ohgiraffers.jwtrestapi.review.entity.ReviewAndMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewAndMemberRepository extends JpaRepository<ReviewAndMember, Integer> {
    Page<ReviewAndMember> findByProductCode(Integer integer, Pageable pageable);

    Optional<ReviewAndMember> findByReviewCode(int reviewCode);
}
