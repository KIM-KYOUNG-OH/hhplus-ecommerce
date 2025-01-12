package kr.hhplus.be.server.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductStatisticsRepository extends JpaRepository<ProductStatistics, Long> {
    @Query("""
        SELECT ps
        FROM ProductStatistics ps
        JOIN FETCH ps.product p
        JOIN FETCH p.productOptions po
        WHERE ps.createdAt >= :searchStartDate AND ps.createdAt < :searchEndDate
        """)
    List<ProductStatistics> findListBetween(LocalDateTime searchStartDateTime, LocalDateTime searchEndDateTime);
}
