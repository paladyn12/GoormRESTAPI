package com.ohgiraffers.jwtrestapi.product.service;

import com.ohgiraffers.jwtrestapi.common.Criteria;
import com.ohgiraffers.jwtrestapi.product.dto.ProductDTO;
import com.ohgiraffers.jwtrestapi.product.entity.Product;
import com.ohgiraffers.jwtrestapi.product.repository.ProductRepository;
import com.ohgiraffers.jwtrestapi.util.FileUploadUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Value("${image.image-dir}")
    private String IMAGE_DIR;

    @Value("${image.image-url}")
    private String IMAGE_URL;

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    /* Entity <-> DTO */
    private final ModelMapper modelMapper;

    public int selectProductTotal() {

        log.info("[ProductService] selectProductTotal() start!!");

        List<Product> productList = productRepository.findByProductOrderable("Y");

        log.info("[ProductService] selectProductTotal() end!!");

        return productList.size();
    }

    public Object selectProductListWithPaging(Criteria cri) {

        log.info("[ProductService] selectProductListWithPaging() start!!");

        int index = cri.getPageNum() - 1;
        int count = cri.getAmount();

        Pageable paging = PageRequest.of(index , count , Sort.by("productCode").descending());

        Page<Product> result = productRepository.findByProductOrderable("Y" , paging);

        List<Product> productList = result.getContent();

        // 이미지 관련 처리 -> 여러분들이 신경쓰지 않아도 됩니다.
        for(int i = 0; i < productList.size(); i++) {
            productList.get(i).setProductImageUrl(IMAGE_URL + productList.get(i).getProductImageUrl());
        }

        log.info("[ProductService] selectProductListWithPaging() end!!");

        // productList의 Product 객체들을 순회하며 ProductDTO로 변환
        return productList.stream().map(product -> modelMapper.map(product, ProductDTO.class));
    }

    public Object selectProduct(int productCode) {
        log.info("[ProductService] selectProduct() Start");

        Product product = productRepository.findById(productCode).get();
        product.setProductImageUrl(IMAGE_URL + product.getProductImageUrl());

        log.info("[ProductService] selectProduct() End");

        return modelMapper.map(product, ProductDTO.class);
    }

    public Object selectSearchProductList(String search) {
        log.info("[ProductService] selectSearchProductList() Start");
        log.info("[ProductService] searchValue : {}", search);

        List<Product> productListWithSearchValue = productRepository.findByProductNameContaining(search);
        for (int i = 0; i < productListWithSearchValue.size(); i++) {
            productListWithSearchValue.get(i).setProductImageUrl(IMAGE_URL + productListWithSearchValue.get(i).getProductImageUrl());

        }
//        log.info("[ProductService] productListWithSearchValue : {}", productListWithSearchValue);


        log.info("[ProductService] selectSearchProductList() End");

        return productListWithSearchValue.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    public Object selectProductListAboutMeal() {
        log.info("[ProductService] selectProductListAboutMeal() Start");

        List<Product> productListAboutMeal = productRepository.findByCategoryCode(1);
        for (int i = 0; i < productListAboutMeal.size(); i++) {
            productListAboutMeal.get(i).setProductImageUrl(IMAGE_URL + productListAboutMeal.get(i).getProductImageUrl());

        }

        log.info("[ProductService] selectProductListAboutMeal() End");

        return productListAboutMeal.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    public Object selectProductListAboutDessert() {
        log.info("[ProductService] selectProductListAboutDessert() Start");

        List<Product> productListAboutDessert = productRepository.findByCategoryCode(2);
        for (int i = 0; i < productListAboutDessert.size(); i++) {
            productListAboutDessert.get(i).setProductImageUrl(IMAGE_URL + productListAboutDessert.get(i).getProductImageUrl());

        }

        log.info("[ProductService] selectProductListAboutDessert() End");

        return productListAboutDessert.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());


    }

    public Object selectProductListAboutBeverage() {
        log.info("[ProductService] selectProductListAboutBeverage() Start");

        List<Product> productListAboutBeverage = productRepository.findByCategoryCode(3);
        for (int i = 0; i < productListAboutBeverage.size(); i++) {
            productListAboutBeverage.get(i).setProductImageUrl(IMAGE_URL + productListAboutBeverage.get(i).getProductImageUrl());

        }

        log.info("[ProductService] selectProductListAboutBeverage() End");

        return productListAboutBeverage.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());

    }

    public int selectProductTotalForAdmin() {
        log.info("[ProductService] selectProductTotalForAdmin() Start");


        log.info("[ProductService] selectProductTotalForAdmin() End");

        return 0;
    }

    public Object selectProductListWithPagingForAdmin(Criteria cri) {
        log.info("[ProductService] selectProductListWithPagingForAdmin() Start");


        log.info("[ProductService] selectProductListWithPagingForAdmin() End");

        return null;
    }

    public ProductDTO selectProductForAdmin(int productCode) {
        log.info("[ProductService] selectProductForAdmin() Start");

        Product product = productRepository.findById(productCode).get();
        product.setProductImageUrl(IMAGE_URL + product.getProductImageUrl());

        log.info("[ProductService] selectProductForAdmin() End");


        return modelMapper.map(product, ProductDTO.class);
    }

    @Transactional
    public String insertProduct(ProductDTO productDTO, MultipartFile productImage) {
        log.info("[ProductService] insertProduct() Start");

        String imageName = UUID.randomUUID().toString().replace("-", "");
        String replaceFileName = null;
        int result = 0;

        try {
            replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, productImage);

            productDTO.setProductImageUrl(replaceFileName);

            // 화면에서 전달 받은 DTO 객체를 Entity로 변환
            Product insertProduct = modelMapper.map(productDTO, Product.class);
            productRepository.save(insertProduct);

            // 정상 완료 시 반영될 코드
            result = 1;
        } catch (IOException e) {
            FileUploadUtils.deleteFile(IMAGE_DIR, replaceFileName);
            throw new RuntimeException(e);
        }

        log.info("[ProductService] insertProduct() End");
        return (result > 0) ? productDTO.getProductName() + "상품 등록 성공" : "상품 등록 실패";
    }

    @Transactional
    public String updateProduct() {
        log.info("[ProductService] updateProduct() Start");
//        log.info("[ProductService] productDTO : {}", productDTO);

        log.info("[ProductService] updateProduct End");
        return "";
    }
}