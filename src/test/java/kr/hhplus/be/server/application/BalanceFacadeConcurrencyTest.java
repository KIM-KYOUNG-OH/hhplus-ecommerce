package kr.hhplus.be.server.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BalanceFacadeConcurrencyTest {

    @Autowired
    private BalanceFacade balanceFacade;

    @DisplayName("한 회원에게 1000원씩 10회 동시에 충전할 경우, 총 10000원 충전됌")
    @Test
    @Transactional
    void chargeConcurrentTest() throws InterruptedException {
        int threadCount = 10;
        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    balanceFacade.chargeBalance(1L, 1000L);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        long finalBalance = balanceFacade.getBalance(1L);
        assertEquals(10000L, finalBalance, "잔액이 10000원이 되어야 합니다.");
    }

}