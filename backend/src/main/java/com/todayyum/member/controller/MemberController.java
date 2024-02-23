package com.todayyum.member.controller;

import com.todayyum.auth.userDetails.CustomUserDetails;
import com.todayyum.global.dto.response.BaseResponse;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import com.todayyum.member.application.AddMemberUseCase;
import com.todayyum.member.application.FindMemberUseCase;
import com.todayyum.member.application.ModifyMemberUseCase;
import com.todayyum.member.application.RemoveMemberUseCase;
import com.todayyum.member.domain.ValidationResult;
import com.todayyum.member.dto.request.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Slf4j
public class MemberController {

    private final AddMemberUseCase addMemberUseCase;
    private final FindMemberUseCase findMemberUseCase;
    private final ModifyMemberUseCase modifyMemberUseCase;
    private final RemoveMemberUseCase removeMemberUseCase;

    @PostMapping
    public ResponseEntity<?> memberAdd(@RequestBody @Valid MemberAddRequest memberAddRequest) {
        return BaseResponse.createResponseEntity(ResponseCode.CREATED, addMemberUseCase.addMember(memberAddRequest));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<?> memberDetail(@PathVariable UUID memberId) {
        return BaseResponse.createResponseEntity(ResponseCode.OK, findMemberUseCase.findMember(memberId));
    }

    @DeleteMapping
    public ResponseEntity<?> memberRemove(Authentication authentication) {

        removeMemberUseCase.removeMember(getUserDetails(authentication).getMemberId());
        return BaseResponse.createResponseEntity(ResponseCode.OK);
    }

    @PatchMapping("/nicknames")
    public ResponseEntity<?> nicknameModify(Authentication authentication,
                                            @Valid NicknameModifyRequest nicknameModifyRequest) {

        nicknameModifyRequest.setMemberId(getUserDetails(authentication).getMemberId());
        modifyMemberUseCase.modifyNickname(nicknameModifyRequest);

        return BaseResponse.createResponseEntity(ResponseCode.OK);
    }

    @PatchMapping("/passwords")
    public ResponseEntity<?> passwordModify(Authentication authentication,
                                            @Valid PasswordModifyRequest passwordModifyRequest) {

        passwordModifyRequest.setMemberId(getUserDetails(authentication).getMemberId());
        modifyMemberUseCase.modifyPassword(passwordModifyRequest);

        return BaseResponse.createResponseEntity(ResponseCode.OK);
    }

    @PatchMapping("/comments")
    public ResponseEntity<?> commentModify(Authentication authentication,
                                           @Valid CommentModifyRequest commentModifyRequest) {

        commentModifyRequest.setMemberId(getUserDetails(authentication).getMemberId());
        modifyMemberUseCase.modifyComment(commentModifyRequest);

        return BaseResponse.createResponseEntity(ResponseCode.OK);
    }

    @PostMapping("/profiles")
    public ResponseEntity<?> profileModify(Authentication authentication,
                                           @Valid ProfileModifyRequest profileModifyRequest) {

        profileModifyRequest.setMemberId(getUserDetails(authentication).getMemberId());
        modifyMemberUseCase.modifyProfile(profileModifyRequest);

        return BaseResponse.createResponseEntity(ResponseCode.OK);
    }

    @GetMapping("/nicknames/validations")
    public ResponseEntity<?> nicknameValidate(String nickname) {
        if(nickname == null || nickname.isEmpty()) {
            throw new CustomException(ResponseCode.EMPTY_INPUT);
        }

        ValidationResult validationResult = findMemberUseCase.validateNickname(nickname);

        switch (validationResult) {
            case INVALID:
                return BaseResponse.createResponseEntity(ResponseCode.INVALID_NICKNAME);
            case DUPLICATED:
                return BaseResponse.createResponseEntity(ResponseCode.DUPLICATE_NICKNAME);
            case VALID:
                return BaseResponse.createResponseEntity(ResponseCode.VALID_NICKNAME);
            default:
                return BaseResponse.createResponseEntity(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/emails/validations")
    public ResponseEntity<?> emailValidate(String email) {
        if(email == null || email.isEmpty()) {
            throw new CustomException(ResponseCode.EMPTY_INPUT);
        }

        ValidationResult validationResult = findMemberUseCase.validateEmail(email);

        switch (validationResult) {
            case INVALID:
                return BaseResponse.createResponseEntity(ResponseCode.INVALID_EMAIL);
            case DUPLICATED:
                return BaseResponse.createResponseEntity(ResponseCode.DUPLICATE_EMAIL);
            case VALID:
                return BaseResponse.createResponseEntity(ResponseCode.VALID_EMAIL);
            default:
                return BaseResponse.createResponseEntity(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    public CustomUserDetails getUserDetails(Authentication authentication) {
        return (CustomUserDetails) authentication.getPrincipal();
    }
}
