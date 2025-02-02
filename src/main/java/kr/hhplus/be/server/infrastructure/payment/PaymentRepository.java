package kr.hhplus.be.server.infrastructure.payment;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import kr.hhplus.be.server.domain.pay.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "10000")})
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT p
        FROM Payment p
        JOIN FETCH Order o ON o.orderId = p.order.orderId
        WHERE o.orderId = :orderId
    """)
    Optional<Payment> findByIdWithLock(@Param("orderId") Long orderId);
}
