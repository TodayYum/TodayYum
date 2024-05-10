package com.todayyum.board.application;

import com.todayyum.board.application.repository.BoardRepository;
import com.todayyum.board.application.repository.BoardTagRepository;
import com.todayyum.board.application.repository.TagRepository;
import com.todayyum.board.domain.Board;
import com.todayyum.board.domain.Tag;
import com.todayyum.board.dto.request.BoardModifyRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModifyBoardUseCase {
    private final BoardRepository boardRepository;
    private final BoardTagRepository boardTagRepository;
    private final TagRepository tagRepository;

    @Transactional
    public Long modifyBoard(BoardModifyRequest boardModifyRequest) {
        Board board = boardRepository.findById(boardModifyRequest.getId());

        board.change(boardModifyRequest);

        boardTagRepository.deleteByBoardIdAndContents(board.getId(), boardModifyRequest.getRemovedTags());

        addBoardTag(board.getId(), boardModifyRequest.getTags());

        return boardRepository.save(board).getId();
    }

    @Transactional
    public void addBoardTag(Long boardId, List<String> tags) {
        if(tags == null || tags.isEmpty()) return;

        for(String content : tags) {
            Tag tag = tagRepository.findByContent(content);

            if(tag == null) {
                tag = tagRepository.save(content);
            }

            if(boardTagRepository.existsByBoardIdAndContent(boardId, content)) continue;

            boardTagRepository.save(tag.getId(), boardId);
        }
    }
}
