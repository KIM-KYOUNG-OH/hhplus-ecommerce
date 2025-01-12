package kr.hhplus.be.server.interfaces.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.product.ProductService;
import kr.hhplus.be.server.interfaces.common.responses.ApiResponse;
import kr.hhplus.be.server.interfaces.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "product", description = "상품 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "상품 목록 조회",
            description = "상품 목록을 조회합니다."
    )
    @GetMapping("/products")
    public ApiResponse<List<ProductDto>> getProducts(@Parameter(description = "현재 페이지", required = true, example = "1")
                                                        @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                                                        @Parameter(description = "페이지 크기", required = true, example = "10")
                                                        @RequestParam(name = "size", defaultValue = "10") int size
                                                        ) {
        Pageable page = PageRequest.of(pageNumber - 1, size);
        Page<ProductDto> response = productService.findAll(page).map(ProductDto::from);
        return ApiResponse.ok(response.getContent());
    }

    @Operation(
            summary = "인기 상품 목록 조회",
            description = "최근 3일간 가장 많이 팔린 상위 5개 상품 정보를 조회합니다."
    )
    @GetMapping("/products/popular")
    public ApiResponse<List<ProductDto>> getBestProducts() {
        List<Product> bestProducts = productService.findBestProductsBetween(LocalDate.now().minusDays(3), LocalDate.now());
        List<ProductDto> response = bestProducts.stream()
                .map(ProductDto::from)
                .toList();
        return ApiResponse.ok(response);
    }
}
