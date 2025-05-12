package com.ohgiraffers.jwtrestapi.product.repository;

import com.ohgiraffers.jwtrestapi.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product , Integer> {

    // 판매 가능한 메뉴 조회
    List<Product> findByProductOrderable(String y);

    // 페이징 처리가 된 메뉴 조회
    Page<Product> findByProductOrderable(String y , Pageable pageable);

    // 입력한 검색어를 포함하는 제품 리스트 조회
    List<Product> findByProductNameContaining(String search);

    List<Product> findByCategoryCode(int i);
}