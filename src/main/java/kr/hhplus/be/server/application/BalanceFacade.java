package kr.hhplus.be.server.application;

import kr.hhplus.be.server.domain.member.Member;
import kr.hhplus.be.server.domain.member.MemberService;
import kr.hhplus.be.server.domain.wallet.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class BalanceFacade {

    private final MemberService memberService;
    private final WalletService walletService;

    @Transactional(readOnly = true)
    public Long getBalance(Long memberId) {
        Member findMember = memberService.findBy(memberId);
        return walletService.findBalanceById(findMember.getMemberId());
    }

    @Transactional
    public void chargeBalance(Long memberId, Long chargeAmount) {
        Member findMember = memberService.findBy(memberId);
        walletService.chargeWithLock(findMember.getMemberId(), chargeAmount);
    }
}
