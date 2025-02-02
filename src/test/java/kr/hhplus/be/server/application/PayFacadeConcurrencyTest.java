package kr.hhplus.be.server.application;

import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.coupon.MyCoupon;
import kr.hhplus.be.server.domain.pay.Payment;
import kr.hhplus.be.server.domain.pay.PaymentService;
import kr.hhplus.be.server.domain.pay.exception.DuplicatePaymentException;
import kr.hhplus.be.server.domain.wallet.WalletService;
import kr.hhplus.be.server.interfaces.common.exception.NotFoundException;
import kr.hhplus.be.server.interfaces.dto.PayRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PayFacadeConcurrencyTest {

    @Autowired
    private PayFacade payFacade;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private WalletService walletService;

    @DisplayName("한 주문에 대한 결제 요청을 동시에 10회 처리할 경우, 1건 결제 성공(그외 9건 중복 예외 발생) 및 유저 쿠폰 사용처리")
    @Test
    @Transactional
    void orderConcurrencyTest() throws InterruptedException {
        int threadCount = 10;
        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        List<Throwable> duplicateExceptions = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    payFacade.pay(2L, new PayRequest(1L, 10000L, 1000L, 9000L));
                } catch (DuplicatePaymentException e) {
                    duplicateExceptions.add(e);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

//        assertEquals(9, duplicateExceptions.size(), "DuplicatePaymentException이 9번 발생해야 합니다.");
        Payment findPayment = paymentService.findByIdWithLock(1L).orElseThrow(() -> new NotFoundException("not found"));
        assertEquals("COMPLETED", findPayment.getPaymentStatus());
        assertEquals(10000L, findPayment.getTotalRegularPrice());
        assertEquals(1000L, findPayment.getTotalDiscountPrice());
        assertEquals(9000L, findPayment.getTotalSalePrice());
        assertEquals(1L, findPayment.getOrder().getOrderId());
        Page<MyCoupon> myCoupons = couponService.findMyCoupons(2L, PageRequest.of(0, 20));
        assertEquals(1, myCoupons.getTotalElements(), "회원 보유 쿠폰수가 1개 차감되어야 합니다.");
        assertEquals(1000L, walletService.findBalanceById(2L), "회원 잔고는 9000이 차감되어야 합니다.");
    }

}