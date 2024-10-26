package com.todayyum.board.application;

import com.todayyum.board.application.repository.BoardImageRepository;
import com.todayyum.board.application.repository.BoardRepository;
import com.todayyum.board.application.repository.BoardTagRepository;
import com.todayyum.board.application.repository.TagRepository;
import com.todayyum.board.domain.Board;
import com.todayyum.board.domain.BoardImage;
import com.todayyum.board.domain.Tag;
import com.todayyum.board.dto.request.BoardAddRequest;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import com.todayyum.global.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
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
    @Autowired
    private ApplicationContext context;
    @Autowired
    @Qualifier("threadPoolTaskExecutor")
    private Executor threadPoolTaskExecutor;

    @Transactional
    public Long addBoard(BoardAddRequest boardAddRequest) {
        Board board = Board.createBoard(boardAddRequest);

        //이미지 업로드
        CompletableFuture<List<String>> linksFuture = context
                .getBean(AddBoardUseCase.class)
                .uploadImage(boardAddRequest.getImages());

        //내용 저장
        Long boardId = boardRepository.save(board).getId();

        //태그 저장
        addBoardTag(boardId, boardAddRequest.getTags());

        //이미지 저장
        linksFuture.thenAccept(links -> {
            if (!links.isEmpty()) {
                addBoardImage(boardId, links);
            }
        }).exceptionally(ex -> {
            // 예외 발생 시 처리
            boardRepository.deleteById(boardId);

            throw new CustomException(ResponseCode.S3_UPLOAD_FAILED);
        });

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

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<List<String>> uploadImage(List<MultipartFile> images) {
        if(images == null || images.isEmpty()) return CompletableFuture.completedFuture(Collections.emptyList());

        List<CompletableFuture<String>> uploadFutures = images.stream()
                .map(image -> CompletableFuture.supplyAsync(() -> s3Util.uploadFile(image), threadPoolTaskExecutor))
                .toList();

        return CompletableFuture.allOf(uploadFutures.toArray(new CompletableFuture[0]))
                .thenApply(v -> uploadFutures.stream()
                        .map(CompletableFuture::join)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
    }

    @Transactional
    public void addBoardImage(Long boardId, List<String> imageUrls) {
        if(imageUrls == null || imageUrls.isEmpty()) return;

        List<BoardImage> boardImages = imageUrls.stream()
                .map(link -> BoardImage.createBoardImage(boardId, link))
                .collect(Collectors.toList());

        boardImageRepository.saveAll(boardImages);
    }
}
