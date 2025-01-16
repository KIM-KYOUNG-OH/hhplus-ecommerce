package kr.hhplus.be.server.application;

import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.coupon.MyCoupon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
class CouponFacadeConcurrencyTest {

    @Autowired
    private CouponFacade couponFacade;

    @Autowired
    private CouponService couponService;

    @DisplayName("한 회원에게 쿠폰 발급을 10회 동시에 요청할 경우, 해당 유저에게 쿠폰 총 10개가 발급되며 쿠폰 재고가 10개 차감됌")
    @Test
    void issueCouponConcurrencyTest() throws InterruptedException {
        int threadCount = 10;
        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    couponFacade.issueCoupon(1L, 1L);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        Coupon findCoupon = couponService.findCouponById(1L);
        assertEquals(90, findCoupon.getQuantity(), "쿠폰이 10개 차감돼어야 합니다.");
        Page<MyCoupon> coupons = couponFacade.getCoupons(1L, PageRequest.of(0, 20));
        assertEquals(10, coupons.getTotalElements(), "발급된 쿠폰 갯수가 10개이어야 합니다.");
    }

}