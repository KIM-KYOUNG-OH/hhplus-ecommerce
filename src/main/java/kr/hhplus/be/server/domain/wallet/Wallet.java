package kr.hhplus.be.server.domain.wallet;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.member.Member;
import kr.hhplus.be.server.domain.wallet.exception.InsufficientBalanceException;
import kr.hhplus.be.server.interfaces.common.exception.InvalidNumberParamException;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
public class Wallet {

    @Id
    private Long memberId;

    @MapsId("memberId")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @Column(nullable = false)
    private Long balance;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public static Wallet of(Long memberId, Long balance) {
        return Wallet.builder()
                .memberId(memberId)
                .balance(balance)
                .build();
    }

    public void addBalance(Long chargeAmount) {
        if (chargeAmount <= 0) throw new InvalidNumberParamException("충전할 금액은 양의 실수이어야 합니다.");
        balance += chargeAmount;
    }

    public void dedctBalance(Long deductAmount) {
        if (deductAmount <= 0) {
            throw new InvalidNumberParamException("차감할 금액은 양의 실수이어야 합니다.");
        } else if (deductAmount >= balance) {
            throw new InsufficientBalanceException("잔액이 부족합니다.");
        }

        balance -= deductAmount;
    }
}
