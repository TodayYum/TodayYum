package com.todayyum.board.infra.database;

import com.todayyum.board.application.repository.BoardTagRepository;
import com.todayyum.board.infra.entity.BoardEntity;
import com.todayyum.board.infra.entity.BoardTagEntity;
import com.todayyum.board.infra.entity.TagEntity;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BoardTagRepositoryImpl implements BoardTagRepository {

    private final JpaBoardTagRepository jpaBoardTagRepository;
    private final JpaBoardRepository jpaBoardRepository;
    private final JpaTagRepository jpaTagRepository;

    @Override
    public void save(Long tagId, Long boardId) {
        TagEntity tagEntity = jpaTagRepository.findById(tagId)
                .orElseThrow(() -> new CustomException(ResponseCode.OK));

        BoardEntity boardEntity = jpaBoardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ResponseCode.OK));

        BoardTagEntity boardTagEntity = BoardTagEntity.builder()
                .tag(tagEntity)
                .board(boardEntity)
                .build();

        jpaBoardTagRepository.save(boardTagEntity);
    }

    @Override
    public void deleteByBoardIdAndContents(Long boardId, List<String> tags) {
        jpaBoardTagRepository.deleteByBoardIdAndContents(boardId, tags);
    }

    @Override
    public boolean existsByBoardIdAndContent(Long boardId, String content) {
        return jpaBoardTagRepository.existsByBoardIdAndContent(boardId, content);
    }
}
