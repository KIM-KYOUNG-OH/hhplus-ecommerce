package kr.hhplus.be.server.application;

import kr.hhplus.be.server.domain.member.Member;
import kr.hhplus.be.server.domain.member.MemberService;
import kr.hhplus.be.server.domain.order.Order;
import kr.hhplus.be.server.domain.order.OrderService;
import kr.hhplus.be.server.domain.pay.Payment;
import kr.hhplus.be.server.domain.pay.PaymentService;
import kr.hhplus.be.server.domain.pay.PaymentStatus;
import kr.hhplus.be.server.domain.wallet.WalletService;
import kr.hhplus.be.server.interfaces.dto.PayRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

        Order order = orderService.findById(request.getOrderId());
        Payment payment = Payment.of(PaymentStatus.COMPLETED.name(), request.getTotalRegularPrice(), request.getTotalDiscountPrice(), request.getTotalSalePrice(), order);
        paymentService.save(payment);
    }
}
