package kr.hhplus.be.server.domain.wallet;

import kr.hhplus.be.server.infrastructure.wallet.TransactionHistoryRepository;
import kr.hhplus.be.server.infrastructure.wallet.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;

    @Transactional(readOnly = true)
    public Long findBalanceById(Long memberId) {
        Wallet wallet = walletRepository.findById(memberId)
                .orElseGet(() -> {
                    Wallet newWallet = Wallet.of(memberId, 0L);
                    return walletRepository.save(newWallet);
                });
        return wallet.getBalance();
    }

    @Transactional
    public void chargeWithLock(Long memberId, Long chargeAmount) {
        Wallet wallet = walletRepository.findByIdWithLock(memberId)
                .orElseGet(() -> {
                    Wallet newWallet = Wallet.of(memberId, 0L);
                    return walletRepository.save(newWallet);
                });
        wallet.addBalance(chargeAmount);

        TransactionHistory transactionHistory = TransactionHistory.charge(chargeAmount);
        transactionHistoryRepository.save(transactionHistory);
    }

    @Transactional(readOnly = true)
    public void deductWithLock(Long memberId, Long deductAmount) {
        Wallet wallet = walletRepository.findByIdWithLock(memberId)
                .orElseGet(() -> {
                    Wallet newWallet = Wallet.of(memberId, 0L);
                    return walletRepository.save(newWallet);
                });
        wallet.dedctBalance(deductAmount);

        TransactionHistory transactionHistory = TransactionHistory.deduct(deductAmount);
        transactionHistoryRepository.save(transactionHistory);
    }
}
