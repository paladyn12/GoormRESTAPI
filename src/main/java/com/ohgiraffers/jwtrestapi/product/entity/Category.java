package com.ohgiraffers.jwtrestapi.product.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "tbl_category")
public class Category {

    @Id
    @Column(name = "category_code")
    private int id;

    @Column(name = "category_name")
    private String categoryName;



}
