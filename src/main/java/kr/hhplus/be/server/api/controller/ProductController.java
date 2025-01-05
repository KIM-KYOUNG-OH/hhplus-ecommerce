package kr.hhplus.be.server.api.controller;

import kr.hhplus.be.server.api.common.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProductController {

    @GetMapping("/products")
    public ApiResponse<Map<String, Object>> getProducts(@RequestParam Integer page, @RequestParam Integer size) {
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

    @GetMapping("/products/best")
    public ApiResponse<Map<String, List<Object>>> getBestProducts(@RequestParam String searchStartDate, @RequestParam String searchEndDate) {
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
