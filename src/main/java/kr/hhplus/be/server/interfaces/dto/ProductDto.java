package kr.hhplus.be.server.interfaces.dto;

import kr.hhplus.be.server.domain.product.Product;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class ProductDto {

    private Long productId;
    private String productName;
    private BrandDto brand;
    private List<ProductOptionDto> productOptions = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private static ModelMapper modelMapper = new ModelMapper();

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class BrandDto {
        private Long brandId;
        private String brandName;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ProductOptionDto {
        private Long productOptionId;
        private String optionName;
        private Long quantity;
        private Long regularPrice;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    public static ProductDto from(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }
}
