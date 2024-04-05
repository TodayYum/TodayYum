package com.todayyum.board.application;

import com.todayyum.board.application.repository.BoardImageRepository;
import com.todayyum.board.application.repository.BoardRepository;
import com.todayyum.board.application.repository.CommentRepository;
import com.todayyum.board.application.repository.TagRepository;
import com.todayyum.board.domain.Board;
import com.todayyum.board.domain.BoardImage;
import com.todayyum.board.domain.Comment;
import com.todayyum.board.domain.Tag;
import com.todayyum.board.dto.response.BoardDetailResponse;
import com.todayyum.board.dto.response.BoardListResponse;
import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Member;
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
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;

    public List<BoardListResponse> listBoardByMember(UUID memberId) {
        List<BoardListResponse> boardListResponses = boardRepository.findByMemberId(memberId).stream()
                .peek(boardListResponse -> {
                    BoardImage image = boardImageRepository.findThumbnailByBoardId(boardListResponse.getId());

                    Optional.ofNullable(image).ifPresent(img -> boardListResponse.changeThumbnail(img.getLink()));
                })
                .collect(Collectors.toList());

        return boardListResponses;
    }

    public BoardDetailResponse detailBoard(Long boardId) {
        Board board = boardRepository.findById(boardId);

        Member boardWriter = memberRepository.findById(board.getMemberId());

        Comment comment = commentRepository.findLastByBoardId(boardId);

        Member commentWriter = null;

        if(comment != null) {
            commentWriter = memberRepository.findById(comment.getMemberId());
        }

        List<BoardImage> boardImages = boardImageRepository.findByBoardId(boardId);

        List<Tag> tags = tagRepository.findByBoardId(boardId);

        BoardDetailResponse boardDetailResponse = createBoardDetailResponse(board, boardWriter, commentWriter, comment, tags, boardImages);

        return boardDetailResponse;
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
