package kr.hhplus.be.server.infrastructure.wallet;

import kr.hhplus.be.server.domain.wallet.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
}
