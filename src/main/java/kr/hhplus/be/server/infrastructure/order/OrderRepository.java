package kr.hhplus.be.server.infrastructure.order;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import kr.hhplus.be.server.domain.order.Order;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
        SELECT o
        FROM Order o
        JOIN FETCH o.orderItems oi
        JOIN FETCH oi.product p
        JOIN FETCH p.productOptions po
        WHERE o.createdAt >= :searchStartDate AND o.createdAt < :searchEndDate
        """)
    List<Order> findListBetween(@Param("searchStartDateTime") LocalDateTime searchStartDateTime, @Param("searchEndDateTime") LocalDateTime searchEndDateTime);

    @Query("""
        SELECT o
        FROM Order o
        JOIN FETCH o.member m
        WHERE o.member.memberId = :memberId
        """)
    List<Order> findMyOrdersById(@Param("memberId") Long memberId);

    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "10000")})
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT o
        FROM Order o
        WHERE o.orderId = :orderId
    """)
    Optional<Order> findByIdWithLock(@Param("orderId") Long orderId);
}
