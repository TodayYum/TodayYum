package com.todayyum.board.dto.response;

import com.todayyum.board.domain.Category;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoardListResponse {

    private Long id;
    private Long yummyCount;
    private Float totalScore;
    private String thumbnail;
    private String tag;
    private String category;
    //태그 관련 변수

    public BoardListResponse(Long id, Float totalScore, Long yummyCount, Category category) {
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
