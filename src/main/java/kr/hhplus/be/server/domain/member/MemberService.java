package kr.hhplus.be.server.domain.member;

import kr.hhplus.be.server.infrastructure.member.MemberRepository;
import kr.hhplus.be.server.interfaces.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member findBy(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("해당 회원을 찾을 수 없습니다."));
    }

    @Transactional
    public void save(Member member) {
        memberRepository.save(member);
    }
}
