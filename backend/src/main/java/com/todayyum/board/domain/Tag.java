package com.todayyum.board.domain;

import com.todayyum.board.infra.entity.TagEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Tag {
    private Long id;
    private String content;

    public static Tag createTag(String content) {
        return Tag.builder()
                .content(content)
                .build();
    }

    public static Tag createTag(TagEntity tagEntity) {
        return Tag.builder()
                .id(tagEntity.getId())
                .content(tagEntity.getContent())
                .build();
    }
}
