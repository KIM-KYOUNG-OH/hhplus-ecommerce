package kr.hhplus.be.server.infrastructure.coupon;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import kr.hhplus.be.server.domain.coupon.MyCoupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MyCouponRepository extends JpaRepository<MyCoupon, Long> {

    @Query("""
        SELECT mc
        FROM MyCoupon mc
        JOIN FETCH mc.member m
        JOIN FETCH mc.coupon c
        WHERE mc.member.memberId = :memberId
       """)
    Page<MyCoupon> findMyCoupons(@Param("memberId") Long memberId, Pageable pageable);

    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "10000")})
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT mc
        FROM MyCoupon mc
        JOIN FETCH mc.member m
        JOIN FETCH mc.coupon c
        WHERE mc.couponIssuedId = :couponIssuedId
        """)
    Optional<MyCoupon> findMyCouponByIdWithLock(@Param("couponIssuedId") Long couponIssuedId);
}
