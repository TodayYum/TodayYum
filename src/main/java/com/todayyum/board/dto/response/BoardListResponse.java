package com.todayyum.board.dto.response;

import com.todayyum.board.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
public class BoardListResponse implements Serializable {

    private Long id;
    private Long yummyCount;
    private Double totalScore;
    private String thumbnail;
    private String tag;
    private String category;

    public BoardListResponse(Long id, Double totalScore, Long yummyCount, Category category) {
        this.id = id;
        this.yummyCount = yummyCount;
        this.totalScore = totalScore;
        this.category = category.name();
    }

    public void changeThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    public void changeTag(String tag) { this.tag = tag; }
}
