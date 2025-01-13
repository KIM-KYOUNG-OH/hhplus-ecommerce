package kr.hhplus.be.server.application;

import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.coupon.MyCoupon;
import kr.hhplus.be.server.domain.external.AsyncExternalService;
import kr.hhplus.be.server.domain.member.Member;
import kr.hhplus.be.server.domain.member.MemberService;
import kr.hhplus.be.server.domain.order.Order;
import kr.hhplus.be.server.domain.order.OrderItem;
import kr.hhplus.be.server.domain.order.OrderService;
import kr.hhplus.be.server.domain.order.OrderStatus;
import kr.hhplus.be.server.domain.product.ProductOption;
import kr.hhplus.be.server.domain.product.ProductService;
import kr.hhplus.be.server.interfaces.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final MemberService memberService;
    private final ProductService productService;
    private final CouponService couponService;
    private final OrderService orderService;
    private final AsyncExternalService asyncExternalService;

    @Transactional
    public Long order(Long memberId, OrderRequest request) {
        Member findMember = memberService.findBy(memberId);

        for (OrderRequest.OrderItem orderItem : request.getOrderItems()) {
            productService.deductQuantityWithLock(orderItem.getProductOptionId(), orderItem.getOrderCount());
        }

        MyCoupon myCoupon = null;
        if (request.getAppliedCouponId() != null) {
             myCoupon = couponService.useCouponWithLock(request.getAppliedCouponId());
        }

        Order order = orderService.save(Order.of(OrderStatus.COMPLETED.name(), findMember, myCoupon));

        request.getOrderItems().forEach(i -> {
            ProductOption findProductOption = productService.findProductOptionById(i.getProductOptionId());
            OrderItem orderItem = OrderItem.of(order, findProductOption.getProduct(), findProductOption, i.getOrderCount());
            orderService.saveOrderItem(orderItem);
        });

        asyncExternalService.sendOrderToExternalApi(order);

        return order.getOrderId();
    }
}
