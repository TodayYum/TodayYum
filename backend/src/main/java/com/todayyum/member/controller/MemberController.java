package com.todayyum.member.controller;

import com.todayyum.member.application.AddMemberUseCase;
import com.todayyum.member.application.FindMemberUseCase;
import com.todayyum.member.dto.request.MemberAddRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Slf4j
public class MemberController {

    private final AddMemberUseCase addMemberUseCase;
    private final FindMemberUseCase findMemberUseCase;

    @PostMapping("/join")
    public ResponseEntity<?> memberAdd(MemberAddRequest memberAddRequest) {
        return new ResponseEntity<>(addMemberUseCase.addMember(memberAddRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<?> memberDetail(@PathVariable Long memberId) {
        return new ResponseEntity<>(findMemberUseCase.findMember(memberId), HttpStatus.OK);
    }

}
