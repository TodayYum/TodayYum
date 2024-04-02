package com.todayyum.board.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class BoardListResponse {

    private Long id;
//    private Long yummyCount;
    private Float totalScore;
    private String thumbnail;
    //태그 관련 변수

    public BoardListResponse(Long id, Float totalScore) {
        this.id = id;
//        this.yummyCount = yummyCount;
        this.totalScore = totalScore;
    }

    public void changeThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
