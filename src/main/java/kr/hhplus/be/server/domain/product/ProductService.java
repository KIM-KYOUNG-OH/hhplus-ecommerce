package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.infrastructure.product.ProductOptionRepository;
import kr.hhplus.be.server.infrastructure.product.ProductRepository;
import kr.hhplus.be.server.infrastructure.product.ProductStatisticsRepository;
import kr.hhplus.be.server.interfaces.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductStatisticsRepository productStatisticsRepository;

    @Transactional(readOnly = true)
    public Page<Product> findAll(Pageable page) {
        return productRepository.findAll(page);
    }

    @Transactional(readOnly = true)
    public List<Product> findBestProductsBetween(LocalDate searchStartDate, LocalDate searchEndDate) {
        if (searchStartDate.isAfter(searchEndDate)) {
            throw new IllegalArgumentException("검색 시작일은 검색 종료일보다 이전값이어야 합니다.");
        }

        List<ProductStatistics> list = productStatisticsRepository.findListBetween(searchStartDate, searchEndDate);

        Map<Long, Long> groupedByProductId = list.stream()
                .collect(Collectors.groupingBy(
                        ps -> ps.getProduct().getProductId(), // 상품명 기준으로 그룹화
                        Collectors.summingLong(ProductStatistics::getOrderQuantity) // 각 그룹에 대해 주문 수량 합산
                ));

        List<Long> bestProductIds = groupedByProductId.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue())) // 내림차순 정렬
                .map(Map.Entry::getKey)
                .toList();

        List<Product> result = new ArrayList<>();
        int count = 0;
        int limit = 5;
        for (Long bestProductId : bestProductIds) {
            if (count >= limit) break;
            Product product = productRepository.findById(bestProductId).orElse(null);
            if (product != null) {
                result.add(product);
                count++;
            }
        }

        return result;
    }

    @Transactional
    public void deductQuantityWithLock(Long productOptionId, Long orderCount) {
        ProductOption findProductOption = productOptionRepository.findByIdWithLock(productOptionId).orElseThrow(() -> new NotFoundException("주문한 상품을 찾을 수 없습니다."));
        findProductOption.deductQuantity(orderCount);
    }

    @Transactional(readOnly = true)
    public ProductOption findByIdWithLock(Long productOptionId) {
        return productOptionRepository.findByIdWithLock(productOptionId).orElseThrow(() -> new NotFoundException("선택한 상품 옵션을 찾을 수 없습니다."));
    }
}
