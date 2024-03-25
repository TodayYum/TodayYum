package com.todayyum.board.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Builder
@Getter
@ToString
public class Comment {
    private Long id;
    private UUID memberId;
    private Long boardId;
    private String content;
}
