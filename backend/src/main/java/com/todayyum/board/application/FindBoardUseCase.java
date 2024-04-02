package com.todayyum.board.application;

import com.todayyum.board.application.repository.BoardImageRepository;
import com.todayyum.board.application.repository.BoardRepository;
import com.todayyum.board.domain.BoardImage;
import com.todayyum.board.dto.response.BoardListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindBoardUseCase {

    private final BoardRepository boardRepository;
    private final BoardImageRepository boardImageRepository;

    public List<BoardListResponse> listBoardByMember(UUID memberId) {
        List<BoardListResponse> boardListResponses = boardRepository.findByMemberId(memberId).stream()
                .peek(boardListResponse -> {
                    BoardImage image = boardImageRepository.findThumbnailByBoardId(boardListResponse.getId());

                    Optional.ofNullable(image).ifPresent(img -> boardListResponse.changeThumbnail(img.getLink()));
                })
                .collect(Collectors.toList());

        return boardListResponses;
    }
}
