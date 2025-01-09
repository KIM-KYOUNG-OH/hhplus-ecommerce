package kr.hhplus.be.server.domain.pay;

import kr.hhplus.be.server.infrastructure.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public void save(Payment payment) {
        paymentRepository.save(payment);
    }
}
