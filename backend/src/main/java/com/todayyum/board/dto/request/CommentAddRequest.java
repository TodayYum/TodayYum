package com.todayyum.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Builder
public class CommentAddRequest {
    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 100, message = "내용은 최대 100자입니다.")
    private String content;

    private UUID memberId;

    private Long boardId;
}
