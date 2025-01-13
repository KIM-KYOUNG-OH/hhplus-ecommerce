package kr.hhplus.be.server.domain.external;

import kr.hhplus.be.server.domain.order.Order;
import kr.hhplus.be.server.interfaces.dto.OrderExternalRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsyncExternalService {

    private final ExternalApiFeignClient externalApiFeignClient;

    @Async
    public void sendOrderToExternalApi(Order order) {
        OrderExternalRequest request = OrderExternalRequest.from(order);
        try {
            boolean response = externalApiFeignClient.sendOrder(request);
            if (response) {
                System.out.println("External API call succeeded");
            } else {
                System.err.println("External API call failed");
            }
        } catch (Exception e) {
            System.err.println("Error during external API call: " + e.getMessage());
        }
    }
}
