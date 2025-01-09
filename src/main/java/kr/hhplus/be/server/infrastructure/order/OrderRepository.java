package kr.hhplus.be.server.infrastructure.order;

import kr.hhplus.be.server.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
        SELECT o
        FROM Order o
        JOIN FETCH o.orderItems oi
        JOIN FETCH oi.product p
        JOIN FETCH p.productOptions po
        WHERE o.createdAt >= :searchStartDate AND o.createdAt < :searchEndDate
        """)
    List<Order> findListBetween(LocalDateTime searchStartDateTime, LocalDateTime searchEndDateTime);
}
