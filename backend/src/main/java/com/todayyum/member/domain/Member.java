package com.todayyum.member.domain;

import com.todayyum.member.dto.request.MemberAddRequest;
import com.todayyum.member.infra.entity.MemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
public class Member {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String comment;
    private String profile;
    private LocalDateTime createdAt;

    public static Member createMember(MemberAddRequest memberAddRequest) {
        return Member.builder()
                .email(memberAddRequest.getEmail())
                .password(memberAddRequest.getPassword())
                .nickname(memberAddRequest.getNickname())
                .build();
    }

    public MemberEntity createEntity() {
        return MemberEntity.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .comment(this.comment)
                .profile(this.profile)
                .build();
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeComment(String comment) {
        this.comment = comment;
    }

    public void changeProfile(String profile) {
        this.profile = profile;
    }
}
