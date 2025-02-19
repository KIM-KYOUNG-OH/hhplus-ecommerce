package kr.hhplus.be.server.application.event;

import kr.hhplus.be.server.domain.order.Order;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
public class OrderCompletedEvent {

    private Order order;

    public static OrderCompletedEvent of(Order order) {
        return OrderCompletedEvent.builder()
                .order(order)
                .build();
    }
}
