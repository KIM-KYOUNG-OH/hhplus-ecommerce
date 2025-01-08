package kr.hhplus.be.server.domain.member;

import kr.hhplus.be.server.infrastructure.member.MemberRepository;
import kr.hhplus.be.server.interfaces.common.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @Test
    void 회원이있을경우_회원검색_성공() {
        Member member = Member.of("김항해");
        when(memberRepository.findById(1L)).thenReturn(Optional.ofNullable(member));

        Member result = memberService.findBy(1L);

        assertNotNull(member);
        assertNotNull(result);
        assertEquals(result.getMemberName(), member.getMemberName());
    }

    @Test
    void 회원이없을경우_회원검색_예외발생() {
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> memberService.findBy(1L));
    }

    @Test
    void 회원저장_성공() {
        Member member = Member.of("김항해");
        when(memberRepository.save(member)).thenReturn(member);

        memberService.save(member);

        verify(memberRepository, times(1)).save(member);
    }
}