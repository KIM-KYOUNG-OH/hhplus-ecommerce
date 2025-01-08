package kr.hhplus.be.server.domain.wallet;

import kr.hhplus.be.server.domain.wallet.exception.InsufficientBalanceException;
import kr.hhplus.be.server.infrastructure.wallet.TransactionHistoryRepository;
import kr.hhplus.be.server.infrastructure.wallet.WalletRepository;
import kr.hhplus.be.server.interfaces.common.exception.InvalidNumberParamException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @InjectMocks
    WalletService walletService;

    @Mock
    WalletRepository walletRepository;

    @Mock
    TransactionHistoryRepository transactionHistoryRepository;

    @Test
    void 잔액이있을때_잔액조회_성공() {
        Wallet wallet = new Wallet(1L, null, 10000L, null, null);
        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));

        Long balance = walletService.findBalanceById(1L);

        assertNotNull(balance);
        assertEquals(balance, 10000L);
        verify(walletRepository, times(0)).save(any(Wallet.class));
    }

    @Test
    void 잔액이null인경우_잔액조회_잔액초기화하고0원리턴() {
        Wallet wallet = Wallet.of(1L, 0L);
        when(walletRepository.findById(1L)).thenReturn(Optional.empty());
        when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);


        Long balance = walletService.findBalanceById(1L);

        assertNotNull(balance);
        assertEquals(balance, 0);
        verify(walletRepository, times(1)).save(any(Wallet.class));
    }

    @Test
    void 잔액이있을경우_잔액충전_충전후이력저장() {
        Wallet wallet = Wallet.of(1L, 10000L);
        when(walletRepository.findByIdWithLock(1L)).thenReturn(Optional.of(wallet));
        when(transactionHistoryRepository.save(any(TransactionHistory.class))).thenReturn(any(TransactionHistory.class));

        walletService.chargeWithLock(1L, 5000L);

        assertEquals(15000L, wallet.getBalance());
        verify(walletRepository, times(0)).save(any(Wallet.class));
    }

    @Test
    void 잔액이null인경우_잔액충전_잔액초기화하고충전하고이력저장() {
        Wallet wallet = Wallet.of(1L, 0L);
        when(walletRepository.findByIdWithLock(1L)).thenReturn(Optional.empty());
        when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);
        when(transactionHistoryRepository.save(any(TransactionHistory.class))).thenReturn(any(TransactionHistory.class));

        walletService.chargeWithLock(1L, 5000L);

        assertEquals(5000L, wallet.getBalance());
        verify(walletRepository, times(1)).save(any(Wallet.class));
        verify(transactionHistoryRepository, times(1)).save(any(TransactionHistory.class));
    }

    @Test
    void 음의정수를충전할경우_잔액충전_예외발생() {
        Long chargeAmount = -1000L;
        Wallet wallet = Wallet.of(1L, 10000L);
        when(walletRepository.findByIdWithLock(1L)).thenReturn(Optional.of(wallet));

        assertThrows(InvalidNumberParamException.class, () -> walletService.chargeWithLock(1L, chargeAmount));
        verify(walletRepository, times(0)).save(any(Wallet.class));
    }

    @Test
    void 잔액이있는경우_잔액차감_차감후이력저장() {
        Wallet wallet = Wallet.of(1L, 10000L);
        when(walletRepository.findByIdWithLock(1L)).thenReturn(Optional.of(wallet));
        when(transactionHistoryRepository.save(any(TransactionHistory.class))).thenReturn(any(TransactionHistory.class));

        walletService.deductWithLock(1L, 5000L);

        assertEquals(5000L, wallet.getBalance());
        verify(walletRepository, times(0)).save(any(Wallet.class));
    }

    @Test
    void 잔액이null인경우_잔액차감_잔액초기화후에러발생() {
        Wallet wallet = Wallet.of(1L, 0L);
        when(walletRepository.findByIdWithLock(1L)).thenReturn(Optional.empty());
        when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);

        assertThrows(InsufficientBalanceException.class, () -> walletService.deductWithLock(1L, 5000L));

        verify(walletRepository, times(1)).save(any(Wallet.class));
    }

    @Test
    void 음의정수를충전할경우_잔액차감_예외발생() {
        Long deductAmount = -1000L;
        Wallet wallet = Wallet.of(1L, 10000L);
        when(walletRepository.findByIdWithLock(1L)).thenReturn(Optional.of(wallet));

        assertThrows(InvalidNumberParamException.class, () -> walletService.deductWithLock(1L, deductAmount));

        verify(walletRepository, times(0)).save(any(Wallet.class));
    }

    @Test
    void 잔액이부족할경우_잔액차감_예외발생() {
        Wallet wallet = Wallet.of(1L, 500L);
        when(walletRepository.findByIdWithLock(1L)).thenReturn(Optional.of(wallet));

        assertThrows(InsufficientBalanceException.class, () -> walletService.deductWithLock(1L, 5000L));

        verify(walletRepository, times(0)).save(any(Wallet.class));
    }
}