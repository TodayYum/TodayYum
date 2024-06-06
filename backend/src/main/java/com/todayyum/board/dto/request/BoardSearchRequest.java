package com.todayyum.board.dto.request;

import com.todayyum.board.domain.Category;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class BoardSearchRequest {

    private List<Category> categories;

    @NotNull(message = "정렬 기준을 지정해주세요.")
    private String sortBy;
}
