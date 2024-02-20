package com.todayyum.member.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Builder
public class CommentModifyRequest {

    private UUID memberId;

    @NotNull
    @Size(max = 30, message = "소개글은 최대 30자입니다.")
    private String comment;
}
