package kr.hhplus.be.server.domain.order;

import kr.hhplus.be.server.infrastructure.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<Order> findListBetween(LocalDate searchStartDate, LocalDate searchEndDate) {
        LocalDateTime searchStartDateTime = searchStartDate.atStartOfDay();
        LocalDateTime searchEndDateTime = searchEndDate.plusDays(1).atStartOfDay();
        return orderRepository.findListBetween(searchStartDateTime, searchEndDateTime);
    }

    @Transactional
    public void save(Order order) {
        orderRepository.save(order);
    }
}
