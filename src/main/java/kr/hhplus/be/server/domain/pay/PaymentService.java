package kr.hhplus.be.server.domain.pay;

import kr.hhplus.be.server.infrastructure.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Transactional(readOnly = true)
    public Optional<Payment> findByIdWithLock(Long orderId) {
        return paymentRepository.findByIdWithLock(orderId);
    }
}
