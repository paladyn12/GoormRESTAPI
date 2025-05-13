package com.ohgiraffers.jwtrestapi.product.dto;

import com.ohgiraffers.jwtrestapi.product.entity.Category;
import lombok.Data;

@Data
public class ProductAndCategoryDTO {

    private int productCode;
    private String productName;
    private String productPrice;
    private String productDescription;
    private String productOrderable;
    private Category category;
    private String productImageUrl;
    private Long productStock;
}
