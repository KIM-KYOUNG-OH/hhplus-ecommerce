package kr.hhplus.be.server.domain.coupon;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.coupon.exception.CouponOutOfStockException;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    @Column(nullable = false)
    private String couponName;

    @Column(nullable = false)
    private String discountType;

    @Column(nullable = false)
    private Long discountAmount;

    @Column(nullable = false)
    private Long quantity;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void deductQuantity() {
        if (quantity <= 0) {
            throw new CouponOutOfStockException("쿠폰 재고가 부족합니다.");
        }

        quantity--;
    }
}
