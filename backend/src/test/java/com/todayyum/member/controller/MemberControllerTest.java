package com.todayyum.member.controller;

import com.todayyum.member.application.AddMemberUseCase;
import com.todayyum.member.application.FindMemberUseCase;
import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Member;
import com.todayyum.member.dto.request.MemberAddRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@SpringBootTest
class MemberControllerTest {

    @Autowired
    PlatformTransactionManager transactionManager;
    TransactionStatus status;
    @Autowired
    AddMemberUseCase addMemberUseCase;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void before() {
        status = transactionManager.getTransaction(new DefaultTransactionDefinition());
    }

    @AfterEach
    void after() {
        transactionManager.rollback(status);
    }

    @DisplayName("회원가입 테스트")
    @Test
    void memberAdd() {
        MemberAddRequest memberAddRequest = new MemberAddRequest();
        memberAddRequest.setEmail("asd1234@naver.com");
        memberAddRequest.setPassword("a123456789");
        memberAddRequest.setNickname("yonggkim");

        Long memberId = addMemberUseCase.addMember(memberAddRequest);

        Member member = memberRepository.findById(memberId);

        Assertions.assertEquals(member.getEmail(), memberAddRequest.getEmail());
        Assertions.assertTrue(bCryptPasswordEncoder.matches(memberAddRequest.getPassword(), member.getPassword()));
        Assertions.assertEquals(member.getNickname(), memberAddRequest.getNickname());
    }

    @Test
    void memberDetail() {
    }

}