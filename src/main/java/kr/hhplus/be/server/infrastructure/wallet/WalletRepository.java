package kr.hhplus.be.server.infrastructure.wallet;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT w FROM Wallet w WHERE w.memberId = :memberId")
    Optional<Wallet> findByIdWithLock(@Param("memberId")Long memberId);
}
