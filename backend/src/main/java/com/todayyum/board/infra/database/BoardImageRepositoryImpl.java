package com.todayyum.board.infra.database;

import com.todayyum.board.application.repository.BoardImageRepository;
import com.todayyum.board.domain.BoardImage;
import com.todayyum.board.infra.entity.BoardEntity;
import com.todayyum.board.infra.entity.BoardImageEntity;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BoardImageRepositoryImpl implements BoardImageRepository {

    private final JpaBoardImageRepository jpaBoardImageRepository;
    private final JpaBoardRepository jpaBoardRepository;

    @Override
    public void saveAll(List<BoardImage> boardImages) {
        if(boardImages.isEmpty()) return;

        BoardEntity boardEntity = jpaBoardRepository
                .findById(boardImages.get(0).getBoardId())
                .orElseThrow(() -> new CustomException(ResponseCode.BOARD_ID_NOT_FOUND));

        List<BoardImageEntity> boardImageEntities = boardImages.stream()
                .map(boardImage -> boardImage.createEntity())
                .peek(boardImageEntity -> boardImageEntity.changeBoard(boardEntity))
                .collect(Collectors.toList());

        jpaBoardImageRepository.saveAll(boardImageEntities).stream()
            .map(BoardImage::createBoardImage)
            .collect(Collectors.toList());
    }

    @Override
    public List<BoardImage> findByBoardId(Long boardId) {
        BoardEntity boardEntity = jpaBoardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ResponseCode.BOARD_ID_NOT_FOUND));

        List<BoardImageEntity> boardImageEntities = jpaBoardImageRepository
                .findByBoard(boardEntity);

        return boardImageEntities.stream()
                .map(BoardImage::createBoardImage)
                .collect(Collectors.toList());
    }

    @Override
    public List<BoardImage> findByMemberId(UUID memberId) {
        return jpaBoardImageRepository.findByMemberId(memberId).stream()
                .map(BoardImage::createBoardImage)
                .collect(Collectors.toList());
    }

    @Override
    public BoardImage findThumbnailByBoardId(Long boardId) {
        return jpaBoardImageRepository.findThumbnailByBoardId(boardId)
                .map(BoardImage::createBoardImage).orElse(null);
    }
}
