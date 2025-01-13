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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PayFacade {

    private final MemberService memberService;
    private final WalletService walletService;
    private final OrderService orderService;
    private final PaymentService paymentService;

    @Transactional
    public void pay(Long memberId, PayRequest request) {
        Member findMember = memberService.findBy(memberId);

        walletService.deductWithLock(findMember.getMemberId(), request.getTotalSalePrice());

        Optional<Payment> existingPaymentOptional = paymentService.findByIdWithLock(request.getOrderId());
        if (existingPaymentOptional.isPresent()) {
            Payment existingPayment = existingPaymentOptional.get();

            if (existingPayment.isCompleted()) {
                throw new DuplicatePaymentException("이미 결제 완료 처리되었습니다.");
            }

            existingPayment.setPaymentStatus(PaymentStatus.COMPLETED.name());
            existingPayment.setTotalRegularPrice(request.getTotalRegularPrice());
            existingPayment.setTotalDiscountPrice(request.getTotalDiscountPrice());
            existingPayment.setTotalSalePrice(request.getTotalSalePrice());
        } else {
            Order order = orderService.findById(request.getOrderId());
            Payment payment = Payment.of(PaymentStatus.COMPLETED.name(), request.getTotalRegularPrice(), request.getTotalDiscountPrice(), request.getTotalSalePrice(), order);
            paymentService.save(payment);
        }
    }
}
