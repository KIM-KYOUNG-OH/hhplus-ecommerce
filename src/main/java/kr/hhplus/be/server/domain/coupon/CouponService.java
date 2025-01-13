package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.domain.member.Member;
import kr.hhplus.be.server.infrastructure.coupon.CouponRepository;
import kr.hhplus.be.server.infrastructure.coupon.MyCouponRepository;
import kr.hhplus.be.server.interfaces.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final MyCouponRepository myCouponRepository;

    @Transactional(readOnly = true)
    public Page<MyCoupon> findMyCoupons(Long memberId, Pageable pageable) {
        return myCouponRepository.findMyCoupons(memberId, pageable);
    }

    @Transactional(readOnly = true)
    public Coupon findCouponById(Long couponId) {
        return couponRepository.findById(couponId).orElseThrow(() -> new NotFoundException("해당 쿠폰을 찾을 수 없습니다."));
    }

    @Transactional
    public void issueCouponWithLock(Member member, Long couponId) {
        Coupon coupon = couponRepository.findCouponByIdWithLock(couponId).orElseThrow(() -> new NotFoundException("해당 쿠폰을 찾을 수 없습니다."));
        coupon.deductQuantity();
        MyCoupon myCoupon = MyCoupon.of(member, coupon);
        myCouponRepository.save(myCoupon);
    }

    @Transactional
    public MyCoupon useCouponWithLock(Long couponIssuedId) {
        MyCoupon myCoupon = myCouponRepository.findMyCouponByIdWithLock(couponIssuedId).orElseThrow(() -> new NotFoundException("해당 쿠폰 발급 이력을 찾을 수 없습니다."));
        myCoupon.use();
        return myCoupon;
    }
}
