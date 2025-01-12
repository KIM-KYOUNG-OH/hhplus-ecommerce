package kr.hhplus.be.server.infrastructure.coupon;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.coupon.MyCoupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MyCouponRepository extends JpaRepository<MyCoupon, Long> {

    @Query("""
        SELECT mc
        FROM MyCoupon mc
        JOIN FETCH mc.member m
        JOIN FETCH mc.coupon c
        WHERE mc.member.memberId = :memberId
       """)
    Page<MyCoupon> findMyCoupons(Long memberId, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("""
        SELECT mc
        FROM MyCoupon mc
        JOIN FETCH mc.member m
        JOIN FETCH mc.coupon c
        WHERE mc.couponIssuedId = :couponIssuedId AND mc.isUsed = false
        """)
    Optional<MyCoupon> findMyCouponByIdWithLock(Long couponIssuedId);
}
