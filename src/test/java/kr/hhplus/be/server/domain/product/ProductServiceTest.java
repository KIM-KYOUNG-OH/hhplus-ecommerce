package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.infrastructure.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductStatisticsRepository productStatisticsRepository;

    @Test
    void 상품목록조회_성공() {
        Pageable pageable = Pageable.ofSize(10);
        when(productRepository.findAll(pageable)).thenReturn(any());

        productService.findAll(pageable);

        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    void 우수상품이5건일때_우수상품조회_5건많이팔린상품순조회() {
        List<ProductStatistics> response = new ArrayList<>();
        Product product1 = new Product(1L, "1번상품", null, null, null, null);
        Product product2 = new Product(2L, "2번상품", null, null, null, null);
        Product product3 = new Product(3L, "3번상품", null, null, null, null);
        Product product4 = new Product(4L, "4번상품", null, null, null, null);
        Product product5 = new Product(5L, "5번상품", null, null, null, null);
        ProductStatistics productStatistics1 = new ProductStatistics(null, LocalDate.of(2025, 1, 1), product1, 1L, null, null);
        ProductStatistics productStatistics2 = new ProductStatistics(null, LocalDate.of(2025, 1, 2), product2, 3L, null, null);
        ProductStatistics productStatistics3 = new ProductStatistics(null, LocalDate.of(2025, 1, 3), product3, 2L, null, null);
        ProductStatistics productStatistics4 = new ProductStatistics(null, LocalDate.of(2025, 1, 4), product4, 4L, null, null);
        ProductStatistics productStatistics5 = new ProductStatistics(null, LocalDate.of(2025, 1, 5), product5, 5L, null, null);
        response.add(productStatistics1);
        response.add(productStatistics2);
        response.add(productStatistics3);
        response.add(productStatistics4);
        response.add(productStatistics5);
        when(productStatisticsRepository.findListBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(response);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productRepository.findById(2L)).thenReturn(Optional.of(product2));
        when(productRepository.findById(3L)).thenReturn(Optional.of(product3));
        when(productRepository.findById(4L)).thenReturn(Optional.of(product4));
        when(productRepository.findById(5L)).thenReturn(Optional.of(product5));

        List<Product> bestProducts = productService.findBestProductsBetween(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 5));

        assertEquals(5, bestProducts.size());
        assertEquals(5L, bestProducts.get(0).getProductId());
        assertEquals(4L, bestProducts.get(1).getProductId());
        assertEquals(2L, bestProducts.get(2).getProductId());
        assertEquals(3L, bestProducts.get(3).getProductId());
        assertEquals(1L, bestProducts.get(4).getProductId());
    }

    @Test
    void 같은상품이여러날짜에걸쳐서여러개일때_우수상품조회_1건조회() {
        List<ProductStatistics> response = new ArrayList<>();
        Product product1 = new Product(1L, "1번상품", null, null, null, null);
        ProductStatistics productStatistics1 = new ProductStatistics(null, LocalDate.of(2025, 1, 1), product1, 1L, null, null);
        ProductStatistics productStatistics2 = new ProductStatistics(null, LocalDate.of(2025, 1, 2), product1, 1L, null, null);
        ProductStatistics productStatistics3 = new ProductStatistics(null, LocalDate.of(2025, 1, 3), product1, 1L, null, null);
        ProductStatistics productStatistics4 = new ProductStatistics(null, LocalDate.of(2025, 1, 4), product1, 1L, null, null);
        ProductStatistics productStatistics5 = new ProductStatistics(null, LocalDate.of(2025, 1, 5), product1, 1L, null, null);
        response.add(productStatistics1);
        response.add(productStatistics2);
        response.add(productStatistics3);
        response.add(productStatistics4);
        response.add(productStatistics5);
        when(productStatisticsRepository.findListBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(response);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

        List<Product> bestProducts = productService.findBestProductsBetween(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 5));

        assertEquals(1, bestProducts.size());
        assertEquals(1L, bestProducts.get(0).getProductId());
    }
    
    @Test
    void 검색시작일이검색종료일보다나중일경우_우수상품조회_예외발생() {
        assertThrows(IllegalArgumentException.class,
                () -> productService.findBestProductsBetween(LocalDate.of(2025, 1, 5), LocalDate.of(2025, 1, 1)));
    }
}