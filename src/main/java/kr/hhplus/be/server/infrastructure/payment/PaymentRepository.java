package kr.hhplus.be.server.infrastructure.payment;

import kr.hhplus.be.server.domain.pay.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
