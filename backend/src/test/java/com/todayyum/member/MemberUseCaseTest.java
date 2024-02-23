package com.todayyum.member;

import com.todayyum.member.application.AddMemberUseCase;
import com.todayyum.member.application.FindMemberUseCase;
import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Member;
import com.todayyum.member.dto.request.MemberAddRequest;
import com.todayyum.member.dto.response.MemberDetailResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@Transactional
public class MemberUseCaseTest {

    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private AddMemberUseCase addMemberUseCase;
    @InjectMocks
    private FindMemberUseCase findMemberUseCase;
    @Spy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @DisplayName("Member UC - 회원 가입 테스트")
    void addMember() {

        //given
        MemberAddRequest memberAddRequest = MemberAddRequest.builder()
                .email("qwerasdf1234@naver.com")
                .nickname("bonnnnnkim")
                .password("a123456789")
                .build();

        Member member = Member.createMember(memberAddRequest);
        UUID uuid = UUID.randomUUID();
        member.changeId(uuid);

        when(memberRepository.save(any(Member.class)))
                .thenReturn(member);

        //when
        UUID memberId = addMemberUseCase.addMember(memberAddRequest);

        //then
        assertEquals(memberId, uuid);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("Member UC - 회원 조회 테스트")
    void findMember() {

        //given
        UUID memberId = UUID.randomUUID();

        Member member = Member.builder()
                .id(memberId)
                .email("qwerasdf1234@naver.com")
                .nickname("bonnnnnkim")
                .password("a123456789")
                .build();

        when(memberRepository.findById(memberId))
                .thenReturn(member);

        //when
        MemberDetailResponse memberDetailResponse = findMemberUseCase.findMember(memberId);

        //then
        assertEquals(member.getEmail(), memberDetailResponse.getEmail());
        assertEquals(member.getNickname(), memberDetailResponse.getNickname());

        verify(memberRepository, times(1)).findById(any(UUID.class));
    }

}
