package kr.hhplus.be.server.application.listener;

import kr.hhplus.be.server.application.event.OrderCompletedEvent;
import kr.hhplus.be.server.domain.external.AsyncExternalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener {

    private final AsyncExternalService asyncExternalService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCompleted(OrderCompletedEvent event) {
        log.info("주문 완료 이벤트 수신 (orderId: {}) - 트랜잭션 커밋 후 실행", event.getOrder().getOrderId());

        asyncExternalService.sendOrderToExternalApi(event.getOrder());
    }
}
