package kr.hhplus.be.server.domain.wallet;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
public class TransactionHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long histNo;

    @Column(nullable = false)
    private String transactionType;

    @Column(nullable = false)
    private Long amount;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public static TransactionHistory charge(Long amount) {
        return TransactionHistory.builder()
                .transactionType(TransactionType.CHARGE.name())
                .amount(amount)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static TransactionHistory deduct(Long amount) {
        return TransactionHistory.builder()
                .transactionType(TransactionType.DEDUCT.name())
                .amount(amount)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
