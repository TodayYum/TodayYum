package com.todayyum.board.dto.request;

import com.todayyum.board.domain.Category;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class BoardSearchRequest {

    private Category category;

    @NotNull(message = "정렬 기준을 지정해주세요.")
    private String sortBy;
}
