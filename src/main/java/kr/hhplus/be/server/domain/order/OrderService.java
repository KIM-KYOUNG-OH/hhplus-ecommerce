package kr.hhplus.be.server.domain.order;

import kr.hhplus.be.server.infrastructure.order.OrderItemRepository;
import kr.hhplus.be.server.infrastructure.order.OrderRepository;
import kr.hhplus.be.server.interfaces.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    public void saveOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    @Transactional(readOnly = true)
    public Order findByIdWithLock(Long orderId) {
        return orderRepository.findByIdWithLock(orderId).orElseThrow(() -> new NotFoundException("주문 정보를 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<Order> findMyOrdersById(Long memberId) {
        return orderRepository.findMyOrdersById(memberId);
    }
}
