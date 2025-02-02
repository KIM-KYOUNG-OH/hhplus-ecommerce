package kr.hhplus.be.server.application;

import kr.hhplus.be.server.domain.order.Order;
import kr.hhplus.be.server.domain.order.OrderService;
import kr.hhplus.be.server.domain.product.ProductOption;
import kr.hhplus.be.server.domain.product.ProductService;
import kr.hhplus.be.server.interfaces.dto.OrderRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OrderFacadeConcurrencyTest {

    @Autowired
    private OrderFacade orderFacade;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @DisplayName("한 회원이 동시에 10회 주문 요청할 경우, 주문 10회 생성 및 상품 재고 20개 차감")
    @Test
    @Transactional
    void orderConcurrencyTest() throws InterruptedException {
        int threadCount = 10;
        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        for (int i = 1; i <= threadCount; i++) {
            final int idx = i;
            executorService.submit(() -> {
                try {
                    orderFacade.order(2L, new OrderRequest(List.of(new OrderRequest.OrderItem(1L, 1L, 2L)), (long) idx));
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        List<Order> findOrders = orderService.findMyOrdersById(2L);
        assertEquals(10, findOrders.size(), "생성된 주문은 10건이어야 합니다.");
        ProductOption findProductOption = productService.findByIdWithLock(1L);
        assertEquals(80, findProductOption.getQuantity(), "상품 재고가 20개 차감되어야 합니다.");
    }
}