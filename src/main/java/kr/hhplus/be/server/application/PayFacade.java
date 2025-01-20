package kr.hhplus.be.server.application;

import kr.hhplus.be.server.domain.member.Member;
import kr.hhplus.be.server.domain.member.MemberService;
import kr.hhplus.be.server.domain.order.Order;
import kr.hhplus.be.server.domain.order.OrderService;
import kr.hhplus.be.server.domain.pay.exception.DuplicatePaymentException;
import kr.hhplus.be.server.domain.pay.Payment;
import kr.hhplus.be.server.domain.pay.PaymentService;
import kr.hhplus.be.server.domain.pay.PaymentStatus;
import kr.hhplus.be.server.domain.wallet.WalletService;
import kr.hhplus.be.server.interfaces.dto.PayRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PayFacade {

    private final MemberService memberService;
    private final WalletService walletService;
    private final OrderService orderService;
    private final PaymentService paymentService;

    @Transactional
    public void pay(Long memberId, PayRequest request) {
        Member findMember = memberService.findBy(memberId);
        log.info("memberId: {}", findMember.getMemberId());

        Optional<Payment> existingPaymentOptional = paymentService.findByIdWithLock(request.getOrderId());
        log.info("existingPaymentOptional: {}", existingPaymentOptional);
        if (existingPaymentOptional.isPresent()) {
            log.info("existingPaymentOptional.isPresent(): {}", existingPaymentOptional.isPresent());
            Payment existingPayment = existingPaymentOptional.get();
            log.info("existingPayment: {}", existingPayment);

            if (existingPayment.isCompleted()) {
                log.info("이미 결제 완료");
                throw new DuplicatePaymentException("이미 결제 완료 처리되었습니다.");
            }

            existingPayment.setPaymentStatus(PaymentStatus.COMPLETED.name());
            existingPayment.setTotalRegularPrice(request.getTotalRegularPrice());
            existingPayment.setTotalDiscountPrice(request.getTotalDiscountPrice());
            existingPayment.setTotalSalePrice(request.getTotalSalePrice());
        } else {
            Order order = orderService.findByIdWithLock(request.getOrderId());
            log.info("orderId: {}", order.getOrderId());
            Payment payment = Payment.of(PaymentStatus.COMPLETED.name(), request.getTotalRegularPrice(), request.getTotalDiscountPrice(), request.getTotalSalePrice(), order);
            paymentService.save(payment);
        }

        walletService.deductWithLock(findMember.getMemberId(), request.getTotalSalePrice());
    }
}
