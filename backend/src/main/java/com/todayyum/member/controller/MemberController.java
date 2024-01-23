package com.todayyum.member.controller;

import com.todayyum.member.application.AddMemberUseCase;
import com.todayyum.member.dto.request.MemberAddRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Slf4j
public class MemberController {

    private final AddMemberUseCase addMemberUseCase;

    @PostMapping("/join")
    public ResponseEntity<?> memberAdd(MemberAddRequest memberAddRequest) {
        addMemberUseCase.addMember(memberAddRequest);
        return null;
    }

}
