package kr.hhplus.be.server.application;

import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.coupon.MyCoupon;
import kr.hhplus.be.server.domain.member.Member;
import kr.hhplus.be.server.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CouponFacade {

    private final MemberService memberService;
    private final CouponService couponService;

    @Transactional(readOnly = true)
    public Page<MyCoupon> getCoupons(Long memberId, Pageable page) {
        Member findMember = memberService.findBy(memberId);
        return couponService.findMyCoupons(findMember.getMemberId(), page);
    }

    @Transactional
    public void issueCoupon(Long memberId, Long couponId) {
        Member findMember = memberService.findBy(memberId);
        couponService.issueCouponWithLock(findMember, couponId);
    }
}
