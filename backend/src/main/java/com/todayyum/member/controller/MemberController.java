package com.todayyum.member.controller;

import com.todayyum.auth.userDetails.CustomUserDetails;
import com.todayyum.global.dto.response.BaseResponse;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import com.todayyum.member.application.*;
import com.todayyum.member.domain.ValidationResult;
import com.todayyum.member.dto.request.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final AddFollowUseCase addFollowUseCase;
    private final RemoveFollowUseCase removeFollowUseCase;

    @PostMapping
    public ResponseEntity<?> memberAdd(@Valid MemberAddRequest memberAddRequest) {
        return BaseResponse.createResponseEntity(ResponseCode.CREATED, addMemberUseCase.addMember(memberAddRequest));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<?> memberDetail(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                          @PathVariable UUID memberId) {
        return BaseResponse.createResponseEntity(ResponseCode.OK,
                findMemberUseCase.findMember(customUserDetails.getMemberId(), memberId));
    }

    @GetMapping("/search")
    public ResponseEntity<?> memberSearchByNickname(@PageableDefault(sort = "createdAt") Pageable pageable,
                                                    @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                    String nickname) {
        return BaseResponse.createResponseEntity(ResponseCode.OK,
                findMemberUseCase.findListByNickname(pageable, customUserDetails.getMemberId(), nickname));
    }

    @DeleteMapping
    public ResponseEntity<?> memberRemove(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        removeMemberUseCase.removeMember(customUserDetails.getMemberId());

        return BaseResponse.createResponseEntity(ResponseCode.OK);
    }

    @PatchMapping("/nicknames")
    public ResponseEntity<?> nicknameModify(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                            @Valid @RequestBody NicknameModifyRequest nicknameModifyRequest) {

        nicknameModifyRequest.setMemberId(customUserDetails.getMemberId());
        modifyMemberUseCase.modifyNickname(nicknameModifyRequest);

        return BaseResponse.createResponseEntity(ResponseCode.OK);
    }

    @PatchMapping("/passwords")
    public ResponseEntity<?> passwordModify(@Valid @RequestBody PasswordModifyRequest passwordModifyRequest) {
        modifyMemberUseCase.modifyPassword(passwordModifyRequest);

        return BaseResponse.createResponseEntity(ResponseCode.OK);
    }

    @PatchMapping("/introductions")
    public ResponseEntity<?> introductionModify(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                           @Valid @RequestBody IntroductionModifyRequest introductionModifyRequest) {

        introductionModifyRequest.setMemberId(customUserDetails.getMemberId());
        modifyMemberUseCase.modifyIntroduction(introductionModifyRequest);

        return BaseResponse.createResponseEntity(ResponseCode.OK);
    }

    @PostMapping("/profiles")
    public ResponseEntity<?> profileModify(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                           @Valid ProfileModifyRequest profileModifyRequest) {

        profileModifyRequest.setMemberId(customUserDetails.getMemberId());
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

    @PostMapping("/{memberId}/follow")
    public ResponseEntity<?> followAdd(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                       @PathVariable UUID memberId) {

        return BaseResponse.createResponseEntity(ResponseCode.CREATED,
                addFollowUseCase.addFollow(customUserDetails.getMemberId(), memberId));
    }

    @DeleteMapping("/{memberId}/follow")
    public ResponseEntity<?> followRemove(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                          @PathVariable UUID memberId) {

        removeFollowUseCase.removeFollow(customUserDetails.getMemberId(), memberId);
        return BaseResponse.createResponseEntity(ResponseCode.OK);
    }

    @GetMapping("/{memberId}/followers")
    public ResponseEntity<?> followerList(@PageableDefault(sort = "createdAt") Pageable pageable,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable UUID memberId) {
        return BaseResponse.createResponseEntity(ResponseCode.OK,
                findMemberUseCase.listFollower(pageable, customUserDetails.getMemberId(), memberId));
    }

    @GetMapping("/{memberId}/followings")
    public ResponseEntity<?> followingList(@PageableDefault(sort = "createdAt") Pageable pageable,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable UUID memberId) {
        return BaseResponse.createResponseEntity(ResponseCode.OK,
                findMemberUseCase.listFollowing(pageable, customUserDetails.getMemberId(), memberId));
    }
}
