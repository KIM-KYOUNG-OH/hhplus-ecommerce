package kr.hhplus.be.server.infrastructure.product;

import kr.hhplus.be.server.domain.product.ProductStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProductStatisticsRepository extends JpaRepository<ProductStatistics, Long> {
    @Query("""
        SELECT ps
        FROM ProductStatistics ps
        JOIN FETCH ps.product p
        JOIN FETCH p.productOptions po
        WHERE ps.targetDate >= :searchStartDate AND ps.targetDate <= :searchEndDate
        """)
    List<ProductStatistics> findListBetween(@Param("searchStartDate") LocalDate searchStartDate, @Param("searchEndDate") LocalDate searchEndDate);
}
