package kr.hhplus.be.server.domain.pay;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.order.Order;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
public class Payment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private PaymentStatus paymentStatus;

    private Long totalRegularPrice;

    private Long totalDiscountPrice;

    private Long totalSalePrice;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Order order;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
