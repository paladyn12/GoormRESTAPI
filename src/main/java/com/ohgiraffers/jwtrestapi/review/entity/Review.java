package com.ohgiraffers.jwtrestapi.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder(toBuilder = true)
@Table(name = "tbl_review")
public class Review {

    @Id
    @Column(name = "review_code")
    private int reviewCode;

    @Column(name = "product_code")
    private int productCode;

    @Column(name = "member_code")
    private int memberCode;

    @Column(name = "review_title")
    private String reviewTitle;

    @Column(name = "review_content")
    private String reviewContent;

    @Column(name = "review_create_date")
    private String reviewCreateDate;

}