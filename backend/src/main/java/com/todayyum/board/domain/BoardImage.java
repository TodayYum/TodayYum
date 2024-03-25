package com.todayyum.board.domain;

import com.todayyum.board.infra.entity.BoardImageEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class BoardImage {
    private Long id;
    private Long boardId;
    private String link;

    public static BoardImage createBoardImage(Long boardId, String link) {
        return BoardImage.builder()
                .boardId(boardId)
                .link(link)
                .build();
    }

    public static BoardImage createBoardImage(BoardImageEntity boardImageEntity) {
        return BoardImage.builder()
                .id(boardImageEntity.getId())
                .boardId(boardImageEntity.getBoard().getId())
                .link(boardImageEntity.getLink())
                .build();
    }

    public BoardImageEntity createEntity() {
        return BoardImageEntity.builder()
                .link(this.getLink())
                .build();
    }
}
