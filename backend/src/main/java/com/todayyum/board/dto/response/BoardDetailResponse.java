package com.todayyum.board.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
public class BoardDetailResponse {

    public Long id; //
    private String content; //
    private UUID memberId; //
    private String category; //
    private String mealTime; //
    private Integer tasteScore; //
    private Integer priceScore; //
    private Integer moodScore; //
    private Double totalScore; //
    private LocalDate ateAt; //
    private Long yummyCount; //
    private List<String> images;
    private String profile;
    private String nickname;
    private String comment;
    private String commentWriter;
    private List<String> tags;
    private boolean isYummy;
}
