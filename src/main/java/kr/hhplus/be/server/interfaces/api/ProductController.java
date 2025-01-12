package kr.hhplus.be.server.interfaces.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.interfaces.common.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "product", description = "상품 API")
@RestController
@RequestMapping("/api")
public class ProductController {

    @Operation(
            summary = "상품 목록 조회",
            description = "상품 목록을 조회합니다."
    )
    @GetMapping("/products")
    public ApiResponse<Map<String, Object>> getProducts(@Parameter(description = "현재 페이지", required = true)
                                                        @RequestParam Long page,
                                                        @Parameter(description = "페이지 크기", required = true)
                                                        @RequestParam Long size) {
        return ApiResponse.ok(Map.of(
                "currentPage", page,
                "pageSize", size,
                "products", List.of(
                        Map.of(
                                "productId", 1L,
                                "productName", "셔츠",
                                "brandId", 10L,
                                "brandName", "나이키",
                                "productOptions", List.of(
                                        Map.of("productOptionId", 1L, "optionName", "blue", "quantity", 50L, "regularPrice", 19900L)
                                )
                        ),
                        Map.of(
                                "productId", 2L,
                                "productName", "바지",
                                "brandId", 15L,
                                "brandName", "아디다스",
                                "productOptions", List.of(
                                        Map.of("productOptionId", 2L, "optionName", "black", "quantity", 30L, "regularPrice", 29900L)
                                )
                        )
                )
        ));
    }

    @Operation(
            summary = "인기 상품 목록 조회",
            description = "최근 3일간 가장 많이 팔린 상위 5개 상품 정보를 조회합니다."
    )
    @GetMapping("/products/popular")
    public ApiResponse<Map<String, List<Object>>> getBestProducts() {
        return ApiResponse.ok(Map.of(
                "bestProducts", List.of(
                        Map.of(
                                "rank", 1L,
                                "productId", 1L,
                                "productName", "셔츠",
                                "brandId", 10L,
                                "brandName", "나이키",
                                "productOptions", List.of(
                                        Map.of("productOptionId", 1L, "optionName", "blue", "quantity", 50L, "regularPrice", 19900L)
                                )
                        )
                )
        ));
    }
}
