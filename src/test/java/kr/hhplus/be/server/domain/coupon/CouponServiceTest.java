package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.domain.member.Member;
import kr.hhplus.be.server.infrastructure.coupon.CouponRepository;
import kr.hhplus.be.server.infrastructure.coupon.MyCouponRepository;
import kr.hhplus.be.server.interfaces.common.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @InjectMocks
    CouponService couponService;

    @Mock
    CouponRepository couponRepository;

    @Mock
    MyCouponRepository myCouponRepository;

    @Captor
    ArgumentCaptor<MyCoupon> myCouponCaptor;

    @Test
    void 쿠폰조회_성공() {
        Coupon coupon = new Coupon(1L, "쿠폰1", DiscountType.RATE.name(), 10L, 10L, LocalDateTime.now(), LocalDateTime.now(), 0);
        when(couponRepository.findById(1L)).thenReturn(Optional.of(coupon));

        Coupon findCoupon = couponService.findCouponById(1L);

        assertNotNull(findCoupon);
        assertEquals(1L, findCoupon.getCouponId());
    }

    @Test
    void 쿠폰이없을경우_쿠폰조회_예외발생() {
        when(couponRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> couponService.findCouponById(1L));
    }

    @Test
    void 선착순쿠폰발급_성공() {
        Member member = Member.of(1L, "홍길동");
        Coupon coupon = new Coupon(1L, "선착순쿠폰", DiscountType.RATE.name(), 10L, 5L, LocalDateTime.now(), LocalDateTime.now(), 0);
        when(couponRepository.findCouponByIdWithLock(1L)).thenReturn(Optional.of(coupon));
        when(myCouponRepository.save(any(MyCoupon.class))).thenReturn(any(MyCoupon.class));

        couponService.issueCouponWithLock(member, 1L);

        verify(myCouponRepository).save(myCouponCaptor.capture());
        assertEquals(myCouponCaptor.getValue().getCoupon().getCouponId(), 1L);
        assertEquals(myCouponCaptor.getValue().getCoupon().getQuantity(), 4L);
    }
}