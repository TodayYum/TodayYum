package com.todayyum.board.application;

import com.todayyum.board.application.repository.BoardImageRepository;
import com.todayyum.board.application.repository.BoardRepository;
import com.todayyum.board.application.repository.BoardTagRepository;
import com.todayyum.board.application.repository.TagRepository;
import com.todayyum.board.domain.Board;
import com.todayyum.board.domain.BoardImage;
import com.todayyum.board.domain.Tag;
import com.todayyum.board.dto.request.BoardAddRequest;
import com.todayyum.global.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddBoardUseCase {

    private final BoardRepository boardRepository;
    private final BoardImageRepository boardImageRepository;
    private final BoardTagRepository boardTagRepository;
    private final TagRepository tagRepository;
    private final S3Util s3Util;

    @Transactional
    public Long addBoard(BoardAddRequest boardAddRequest) {
        Board board = Board.createBoard(boardAddRequest);

        //내용 저장
        Long boardId = boardRepository.save(board).getId();

        //태그 저장
        addBoardTag(boardId, boardAddRequest.getTags());

        //이미지 저장
        addBoardImage(boardId, boardAddRequest.getImages());

        return boardId;
    }

    @Transactional
    public void addBoardTag(Long boardId, List<String> tags) {
        if(tags == null || tags.isEmpty()) return;

        for(String content : tags) {
            Tag tag = tagRepository.findByContent(content);

            if(tag == null) {
                tag = tagRepository.save(content);
            }

            boardTagRepository.save(tag.getId(), boardId);
        }
    }

    @Transactional
    public void addBoardImage(Long boardId, List<MultipartFile> images) {
        if(images == null || images.isEmpty()) return;

        List<BoardImage> boardImages = images.stream()
                .map(image -> s3Util.uploadFile(image))
                .filter(Objects::nonNull)
                .map(link -> BoardImage.createBoardImage(boardId, link))
                .collect(Collectors.toList());

        boardImageRepository.saveAll(boardImages);
    }
}
