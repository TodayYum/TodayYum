package com.todayyum.board.application;

import com.todayyum.board.application.repository.*;
import com.todayyum.board.domain.*;
import com.todayyum.board.dto.request.BoardSearchRequest;
import com.todayyum.board.dto.response.BoardDetailResponse;
import com.todayyum.board.dto.response.BoardListResponse;
import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;
    private final YummyRepository yummyRepository;

    public Page<BoardListResponse> listBoardByMember(Pageable pageable, UUID memberId) {
        return createBoardListResponse(boardRepository.findByMemberId(pageable, memberId));
    }

    public Page<BoardListResponse> listBoardByMemberAndYummy(Pageable pageable, UUID memberId) {
        return createBoardListResponse(boardRepository.findByMemberIdAndYummy(pageable, memberId));
    }

    public Page<BoardListResponse> listBoard(Pageable pageable, BoardSearchRequest boardSearchRequest) {
        return createBoardListResponse(boardRepository.findList(pageable, boardSearchRequest));
    }

    public BoardDetailResponse findBoard(UUID memberId, Long boardId) {
        Board board = boardRepository.findById(boardId);

        Member boardWriter = memberRepository.findById(board.getMemberId());

        Comment comment = commentRepository.findLastByBoardId(boardId);

        Member commentWriter = null;

        if(comment != null) {
            commentWriter = memberRepository.findById(comment.getMemberId());
        }

        List<BoardImage> boardImages = boardImageRepository.findByBoardId(boardId);

        List<Tag> tags = tagRepository.findByBoardId(boardId);

        BoardDetailResponse boardDetailResponse = createBoardDetailResponse(
                board, boardWriter, commentWriter, comment, tags, boardImages);

        Yummy yummy = Yummy.createYummy(memberId, boardId);

        boardDetailResponse.setYummy(yummyRepository.existsByMemberIdAndBoardId(yummy));

        return boardDetailResponse;
    }

    public List<BoardListResponse> boardByYummy() {
        return createBoardListResponse(boardRepository.findTopByYummy());
    }

    public List<BoardListResponse> listBoardByYummy() {
        return createBoardListResponse(boardRepository.findTopListByYummy());
    }

    public Page<BoardListResponse> listBoardByTag(Pageable pageable, String content) {
        Page<BoardListResponse> boardListResponses =
                createBoardListResponse(boardRepository.findListByTag(pageable, content));

        boardListResponses.forEach(boardListResponse -> boardListResponse.setTag(content));

        return boardListResponses;
    }

    private Page<BoardListResponse> createBoardListResponse(Page<BoardListResponse> boardListResponses) {
        return boardListResponses.map(boardListResponse -> {
                    BoardImage image = boardImageRepository.findThumbnailByBoardId(boardListResponse.getId());

                    Optional.ofNullable(image).ifPresent(img -> boardListResponse.changeThumbnail(img.getLink()));

                    Tag tag = tagRepository.findTopByBoardId(boardListResponse.getId());

                    if(tag != null) {
                        boardListResponse.changeTag(tag.getContent());
                    }

                    return boardListResponse;
                });
    }

    private List<BoardListResponse> createBoardListResponse(List<BoardListResponse> boardListResponses) {
        return boardListResponses.stream().peek(boardListResponse -> {
            BoardImage image = boardImageRepository.findThumbnailByBoardId(boardListResponse.getId());

            Optional.ofNullable(image).ifPresent(img -> boardListResponse.changeThumbnail(img.getLink()));

            Tag tag = tagRepository.findTopByBoardId(boardListResponse.getId());

            if(tag != null) {
                boardListResponse.changeTag(tag.getContent());
            }
        }).collect(Collectors.toList());
    }

    private BoardDetailResponse createBoardDetailResponse(
            Board board, Member boardWriter, Member commentWriter,
            Comment comment, List<Tag> tags, List<BoardImage> boardImages) {
        BoardDetailResponse boardDetailResponse = board.createDetailResponse();

        boardDetailResponse.setProfile(boardWriter.getProfile());
        boardDetailResponse.setNickname(boardWriter.getNickname());
        boardDetailResponse.setTags(tags.stream()
                .map(tag -> tag.getContent()).collect(Collectors.toList()));
        boardDetailResponse.setImages(boardImages.stream()
                .map(boardImage -> boardImage.getLink()).collect(Collectors.toList()));

        if(comment != null) {
            boardDetailResponse.setComment(comment.getContent());
        }

        if(commentWriter != null) {
            boardDetailResponse.setCommentWriter(commentWriter.getNickname());
        }

        return boardDetailResponse;
    }
}
