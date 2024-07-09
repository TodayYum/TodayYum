package com.todayyum.member.domain;

import com.todayyum.member.dto.request.MemberAddRequest;
import com.todayyum.member.infra.entity.MemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@ToString
public class Member {
    private UUID id;
    private String email;
    private String password;
    private String nickname;
    private String introduction;
    private String profile;
    private LocalDateTime createdAt;
    private Role role;

    public static Member createMember(MemberAddRequest memberAddRequest) {
        return Member.builder()
                .email(memberAddRequest.getEmail())
                .password(memberAddRequest.getPassword())
                .nickname(memberAddRequest.getNickname())
                .role(Role.USER)
                .build();
    }

    public static Member createMember(MemberEntity memberEntity) {
        return Member.builder()
                .id(memberEntity.getId())
                .email(memberEntity.getEmail())
                .password(memberEntity.getPassword())
                .nickname(memberEntity.getNickname())
                .introduction(memberEntity.getIntroduction())
                .profile(memberEntity.getProfile())
                .createdAt(memberEntity.getCreatedAt())
                .role(memberEntity.getRole())
                .build();
    }

    public MemberEntity createEntity() {
        return MemberEntity.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .introduction(this.introduction)
                .profile(this.profile)
                .role(this.role)
                .createdAt(this.createdAt)
                .build();
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void changeProfile(String profile) {
        this.profile = profile;
    }

    public void changeId(UUID id) {
        this.id = id;
    }
}
