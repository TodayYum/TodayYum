package com.todayyum.board.dto.request;

import com.todayyum.board.domain.Category;
import com.todayyum.board.domain.MealTime;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
public class BoardModifyRequest {

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 100, message = "내용은 최대 100자입니다.")
    private String content;

    private Long id;

    private UUID memberId;

    @NotNull(message = "카테고리를 지정해주세요.")
    private Category category;

    @NotNull(message = "식사 시간을 지정해주세요.")
    private MealTime mealTime;

    @NotNull(message = "맛 평점을 입력해주세요.")
    @Min(value = 0, message = "맛 평점은 최소 0점입니다.")
    @Max(value = 5, message = "맛 평점은 최대 5점입니다.")
    private Integer tasteScore;

    @NotNull(message = "가격 평점을 입력해주세요.")
    @Min(value = 0, message = "가격 평점은 최소 0점입니다.")
    @Max(value = 5, message = "가격 평점은 최대 5점입니다.")
    private Integer priceScore;

    @NotNull(message = "분위기 평점을 입력해주세요.")
    @Min(value = 0, message = "분위기 평점은 최소 0점입니다.")
    @Max(value = 5, message = "분위기 평점은 최대 5점입니다.")
    private Integer moodScore;

    @NotNull(message = "총점을 입력해주세요.")
    @Min(value = 0, message = "총점은 최소 0점입니다.")
    @Max(value = 5, message = "총점은 최대 5점입니다.")
    private Float totalScore;

    @NotNull(message = "날짜를 입력해주세요.")
    private LocalDate ateAt;

    private List<String> tags;

    private List<String> removedTags;
}
