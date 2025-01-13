package kr.hhplus.be.server.domain.pay;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.order.Order;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
public class Payment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(nullable = false)
    private String paymentStatus;

    @Column(nullable = false)
    private Long totalRegularPrice;

    @Column(nullable = false)
    private Long totalDiscountPrice;

    @Column(nullable = false)
    private Long totalSalePrice;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Order order;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public static Payment of(String paymentStatus, Long totalRegularPrice, Long totalDiscountPrice, Long totalSalePrice, Order order) {
        return Payment.builder()
                .paymentStatus(paymentStatus)
                .totalRegularPrice(totalRegularPrice)
                .totalDiscountPrice(totalDiscountPrice)
                .totalSalePrice(totalSalePrice)
                .order(order)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public boolean isCompleted() {
        return paymentStatus.equals(PaymentStatus.COMPLETED.name());
    }
}
