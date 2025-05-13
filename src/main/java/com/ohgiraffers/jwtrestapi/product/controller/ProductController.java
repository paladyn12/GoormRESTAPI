package com.ohgiraffers.jwtrestapi.product.controller;

import com.ohgiraffers.jwtrestapi.common.Criteria;
import com.ohgiraffers.jwtrestapi.common.PageDTO;
import com.ohgiraffers.jwtrestapi.common.PagingResponseDTO;
import com.ohgiraffers.jwtrestapi.common.ResponseDTO;
import com.ohgiraffers.jwtrestapi.product.dto.ProductDTO;
import com.ohgiraffers.jwtrestapi.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    @Operation(summary = "상품 리스트 조회 요청", description = "상품 조회 및 페이징 처리가 진행됩니다.", tags = { "ProductController" })
    @GetMapping("/products")
    public ResponseEntity<ResponseDTO> selectProductListWithPaging(
            @RequestParam(name = "offset" , defaultValue = "1") String offset) {

        log.info("[ProductController] selectProductListWithPaging : " + offset);

        // 제품 갯수
        int total = productService.selectProductTotal();

        Criteria cri = new Criteria(Integer.valueOf(offset) , 10);
        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();

        /* 1. offset 의 번호에 맞는 페이지에 뿌릴 Product 들 */
        pagingResponseDTO.setData(productService.selectProductListWithPaging(cri));

        /* 2. PageDTO(Criteria(보고싶은 페이지, 한 페이지에 표시할 개수), 전체 상품 수) */
        pagingResponseDTO.setPageInfo(new PageDTO(cri, total));

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", pagingResponseDTO));
    }

    @Operation(summary = "상품 상세 조회 요청", description = "상품의 상세 페이지 처리가 진행됩니다.", tags = { "ProductController" })
    @GetMapping("/products/{productCode}")
    public ResponseEntity<ResponseDTO> selectProductDetail(@PathVariable int productCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, productCode + "상품 상세 조회 성공", productService.selectProduct(productCode)));
    }

    @Operation(summary = "검색 상품 리스트 조회 요청", description = "검색어에 해당되는 상품 리스트 조회가 진행됩니다.", tags = { "ProductController" })
    @GetMapping("/products/search")
    public ResponseEntity<ResponseDTO> selectSearchProductList(@RequestParam(name = "s", defaultValue = "all") String search) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, search + "검색 성공", productService.selectSearchProductList(search)));
    }

    @Operation(summary = "식사 상품 리스트 조회 요청", description = "식사 카테고리에 해당하는 상품 리스트 조회가 진행됩니다.", tags = { "ProductController" })
    @GetMapping("/products/meals")
    public ResponseEntity<ResponseDTO> selectProductListAboutMeal() {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "식사 상품 조회", productService.selectProductListAboutMeal()));
    }

    @Operation(summary = "디저트 상품 리스트 조회 요청", description = "디저트 카테고리에 해당하는 상품 리스트 조회가 진행됩니다.", tags = { "ProductController" })
    @GetMapping("/products/dessert")
    public ResponseEntity<ResponseDTO> selectProductListAboutDessert() {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "디저트 상품 조회", productService.selectProductListAboutDessert()));
    }

    @Operation(summary = "음료 상품 리스트 조회 요청", description = "음료 카테고리에 해당하는 상품 리스트 조회가 진행됩니다.", tags = { "ProductController" })
    @GetMapping("/products/beverage")
    public ResponseEntity<ResponseDTO> selectProductListAboutBeverage() {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "음료 상품 조회", productService.selectProductListAboutBeverage()));
    }

    @Operation(summary = "관리자 페이지 상품 리스트 조회 요청", description = "관리자 페이지에서 상품 리스트 조회가 진행됩니다.", tags = { "ProductController" })
    @GetMapping("/products-management")
    public ResponseEntity<ResponseDTO> selectProductListWithPagingForAdmin(
            @RequestParam(name = "offset", defaultValue = "1") String offset) {

        int total = productService.selectProductTotal();

        // 1페이지에 10개 데이터라는 검색 조건을 담을 객체
        Criteria cri = new Criteria(Integer.valueOf(offset), 10);
        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        // DB에서 조회해온 데이터를 담는 setData();
        pagingResponseDTO.setData(productService.selectProductListWithPagingForAdmin(cri));
        pagingResponseDTO.setPageInfo(new PageDTO(cri, total));

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", pagingResponseDTO));
    }

    @Operation(summary = "관리자 페이지 상품 상세 페이지 조회 요청", description = "관리자 페이지에서 상품 상세 페이지 조회가 진행됩니다.", tags = { "ProductController" })
    @GetMapping("/products-management/{productCode}")
    public ResponseEntity<ResponseDTO> selectProductDetailForAdmin(@PathVariable int productCode) {

        return ResponseEntity.ok().body(
                new ResponseDTO(HttpStatus.OK, "관리자 제품 상세 조회 성공", productService.selectProductForAdmin(productCode))
        );
    }

    @Operation(summary = "상품 등록 요청", description = "해당 상품 등록이 진행됩니다.", tags = { "ProductController" })
    @PostMapping(value = "/products")
    public ResponseEntity<ResponseDTO> insertProduct() {

        return null;
    }

    /**
     * @ReqeustBody : Json 형식의 요청 데이터를 객체로 매핑
     *  -> 파일 업로드 같은 멀티파트 데이터는 처리 불가
     * @ModelAttribute : form-data와 file upload 데이터를 함께 처리 가능
     *  -> Image or File 관련 처리는 @ModelAttribute를 사용하는 것이 적합
     */
    @Operation(summary = "상품 수정 요청", description = "해당 상품 수정이 진행됩니다.", tags = { "ProductController" })
    @PutMapping(value = "/products")
    public ResponseEntity<ResponseDTO> updateProduct(@ModelAttribute ProductDTO productDTO, MultipartFile productImage) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.CREATED, "상품 등록 성공", productService.insertProduct(productDTO, productImage)));
    }
}