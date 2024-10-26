package com.todayyum.board;

import com.todayyum.board.application.repository.*;
import com.todayyum.board.domain.*;
import com.todayyum.board.dto.request.BoardSearchRequest;
import com.todayyum.board.dto.response.BoardListResponse;
import com.todayyum.board.dto.response.CommentListResponse;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import com.todayyum.member.WithMockMember;
import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.cache.CacheManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@WithMockMember
@ActiveProfiles("local")
public class BoardRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardTagRepository boardTagRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private YummyRepository yummyRepository;
    @MockBean
    private CacheManager cacheManager;
    private Member member;
    private Board board;
    private String content = "태그태그태그";

    @BeforeEach
    void setup() {
        member = Member.builder()
                .email("test@test.com")
                .nickname("test")
                .password("testtest")
                .build();

        member = memberRepository.save(member);

        board = Board.builder()
                .memberId(member.getId())
                .mealTime(MealTime.LUNCH)
                .ateAt(LocalDate.now(ZoneId.of("Asia/Seoul")))
                .yummyCount(10000L)
                .totalScore(3D)
                .moodScore(3)
                .tasteScore(3)
                .priceScore(3)
                .category(Category.JAPANESE_FOOD)
                .content("호구마 맛있다~")
                .build();

        board = boardRepository.save(board);

        Long tagId = tagRepository.save(content).getId();

        boardTagRepository.save(tagId, board.getId());
    }

    @Test
    @DisplayName("Board Repo - 보드 등록 테스트")
    void addBoard() {
        //given
        Board board = Board.builder()
                .memberId(member.getId())
                .mealTime(MealTime.LUNCH)
                .ateAt(LocalDate.now(ZoneId.of("Asia/Seoul")))
                .yummyCount(0L)
                .totalScore(3D)
                .moodScore(3)
                .tasteScore(3)
                .priceScore(3)
                .category(Category.JAPANESE_FOOD)
                .content("호구마 맛있다~")
                .build();

        //when
        Board savedBoard = boardRepository.save(board);

        //then
        assertEquals(board.getContent(), savedBoard.getContent());
    }

    @Test
    @DisplayName("Board Repo - 보드 목록 조회 테스트(멤버 식별자)")
    void findBoardListByMemberId() {
        //given
        Pageable pageable = Pageable.ofSize(10);

        //when
        Page<BoardListResponse> boardListResponses = boardRepository
                .findByMemberId(pageable, member.getId());

        //then
        assertEquals(board.getCategory().name(), boardListResponses.getContent().get(0).getCategory());
    }

    @Test
    @DisplayName("Board Repo - 보드 삭제 테스트")
    void deleteBoard() {
        //given
        Long boardId = board.getId();

        //when
        boardRepository.deleteById(boardId);

        //then
        CustomException thrown = assertThrows(CustomException.class,
                () -> boardRepository.findById(boardId));
        assertEquals(ResponseCode.BOARD_ID_NOT_FOUND, thrown.getResponseCode());
    }

    @Test
    @DisplayName("Board Repo - 보드 조회 테스트")
    void findBoard() {
        //given
        Long boardId = board.getId();

        //when
        Board savedBoard = boardRepository.findById(boardId);

        //then
        assertEquals(board.getContent(), savedBoard.getContent());
    }

    @Test
    @DisplayName("Board Repo - 보드 목록 조회 테스트")
    void findBoardList() {
        //given
        Pageable pageable = Pageable.ofSize(10);
        BoardSearchRequest boardSearchRequest = BoardSearchRequest.builder()
                .sortBy("yummy")
                .build();

        //when
        Page<BoardListResponse> boardListResponses = boardRepository
                .findList(pageable, boardSearchRequest);

        //then
        assertEquals(board.getCategory().name(), boardListResponses.getContent()
                .get(0).getCategory());
    }

    @Test
    @DisplayName("Board Repo - 오늘의 얌 조회 테스트")
    void findTopBoardByYummy() {
        //when
        List<BoardListResponse> boardListResponses = boardRepository
                .findTopByYummy();

        //then
        assertEquals(board.getCategory().name(), boardListResponses.get(0).getCategory());
    }

    @Test
    @DisplayName("Board Repo - 오늘의 얌 목록 조회 테스트")
    void findTopBoardListByYummy() {
        //when
        List<BoardListResponse> boardListResponses = boardRepository
                .findTopListByYummy();

        //then
        assertEquals(board.getYummyCount(), boardListResponses.get(0).getYummyCount());
    }

    @Test
    @DisplayName("Board Repo - 보드 목록 조회 테스트(태그)")
    void findBoardListByTag() {
        //given
        Pageable pageable = Pageable.ofSize(10);

        //when
        Page<BoardListResponse> boardListResponses = boardRepository.findListByTag(pageable, content);

        //then
        assertEquals(board.getCategory().name(), boardListResponses.getContent()
                .get(boardListResponses.getContent().size() - 1).getCategory());
    }

    @Test
    @DisplayName("Board Repo - 유저가 yummy한 보드 목록 조회 테스트")
    void findBoardListByMemberIdAndYummy() {
        //given
        Pageable pageable = Pageable.ofSize(10);

        yummyRepository.save(Yummy.createYummy(member.getId(), board.getId()));

        //when
        Page<BoardListResponse> boardListResponses = boardRepository.findByMemberIdAndYummy(pageable, member.getId());

        //then
        assertEquals(board.getCategory().name(), boardListResponses.getContent().get(0).getCategory());
    }


    @Test
    @DisplayName("Board Repo - 태그 조회 테스트(내용)")
    void findTagByContent() {
        //given
        String tagContent = "태그태그태그";

        //when
        Tag tag = tagRepository.findByContent(tagContent);

        //then
        assertEquals(tagContent, tag.getContent());
    }

    @Test
    @DisplayName("Board Repo - 태그 조회 테스트(보드 식별자)")
    void findTagByBoardId() {
        //when
        List<Tag> tags = tagRepository.findByBoardId(board.getId());

        //then
        assertEquals(content, tags.get(0).getContent());
    }

    @Test
    @DisplayName("Board Repo - 태그 등록 테스트")
    void addTag() {
        //given
        String newTag = "맛집";

        //when
        Tag tag = tagRepository.save(newTag);

        //then
        assertEquals(newTag, tag.getContent());
    }

    @Test
    @DisplayName("Board Repo - top 태그 조회 테스트(보드 식별)")
    void findTopTagByBoardId() {
        //when
        Tag tag = tagRepository.findTopByBoardId(board.getId());

        //then
        assertEquals(content, tag.getContent());
    }

    @Test
    @DisplayName("Board Repo - 댓글 등록 테스트")
    void addComment() {
        //given
        Comment comment = Comment.builder()
                .boardId(board.getId())
                .content("맛있다잉")
                .modifiedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .memberId(member.getId())
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();

        //when
        comment = commentRepository.save(comment);

        //then
        assertEquals(member.getId(), comment.getMemberId());
    }

    @Test
    @DisplayName("Board Repo - 댓글 삭제 테스트")
    void deleteComment() {
        //given
        Comment comment = Comment.builder()
                .boardId(board.getId())
                .content("맛있다잉")
                .modifiedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .memberId(member.getId())
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();

        comment = commentRepository.save(comment);

        Long commentId = comment.getId();

        //when
        commentRepository.delete(comment);

        //then
        CustomException thrown = assertThrows(CustomException.class,
                () -> commentRepository.findById(commentId));
        assertEquals(ResponseCode.COMMENT_ID_NOT_FOUND, thrown.getResponseCode());
    }

    @Test
    @DisplayName("Board Repo - 댓글 조회 테스트")
    void findComment() {
        //given
        Comment comment = Comment.builder()
                .boardId(board.getId())
                .content("맛있다잉")
                .modifiedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .memberId(member.getId())
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();

        comment = commentRepository.save(comment);

        //when
        Comment savedComment = commentRepository.findById(comment.getId());

        //then
        assertEquals(comment.getId(), savedComment.getId());
    }

    @Test
    @DisplayName("Board Repo - 댓글 목록 조회 테스트(보드 식별자)")
    void findCommentListByBoardId() {
        //given
        Pageable pageable = Pageable.ofSize(10);

        Comment comment = Comment.builder()
                .boardId(board.getId())
                .content("맛있다잉")
                .modifiedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .memberId(member.getId())
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();

        comment = commentRepository.save(comment);

        //when
        Page<CommentListResponse> commentListResponses = commentRepository
                .findByBoardId(board.getId(), pageable);

        //then
        assertEquals(comment.getId(), commentListResponses.getContent().get(0).getId());
    }

    @Test
    @DisplayName("Board Repo - Last 댓글 조회 테스트(보드 식별자)")
    void findLastCommentByBoardId() {
        //given
        Comment comment = Comment.builder()
                .boardId(board.getId())
                .content("맛있다잉")
                .modifiedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .memberId(member.getId())
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();

        comment = commentRepository.save(comment);

        //when
        Comment savedComment = commentRepository.findLastByBoardId(board.getId());

        //then
        assertEquals(comment.getId(), savedComment.getId());
    }

    @Test
    @DisplayName("Board Repo - 보드 태그 등록 테스트")
    void addBoardTag() {
        //given
        String newTag = "맛집";

        Tag tag = tagRepository.save(newTag);

        //when
        boardTagRepository.save(tag.getId(), board.getId());

        //then
        assertTrue(boardTagRepository.existsByBoardIdAndContent(board.getId(), newTag));
    }

    @Test
    @DisplayName("Board Repo - 보드 태그 삭제 테스트")
    void deleteBoardTag() {
        List<String> contents = new ArrayList<>();
        contents.add(content);

        //when
        boardTagRepository.deleteByBoardIdAndContents(board.getId(), contents);

        //then
        assertFalse(boardTagRepository.existsByBoardIdAndContent(board.getId(), content));
    }

    @Test
    @DisplayName("Board Repo - 보드 태그 유무 확인 테스트")
    void existsBoardTagByBoardIdAndContent() {
        //when & then
        assertTrue(boardTagRepository.existsByBoardIdAndContent(board.getId(), content));
    }

    @Test
    @DisplayName("Board Repo - yummy 테스트")
    void addYummy() {
        //given
        Yummy yummy = Yummy.createYummy(member.getId(), board.getId());

        //when
        Yummy savedYummy = yummyRepository.save(yummy);

        //then
        assertEquals(yummy.getBoardId(), savedYummy.getBoardId());
    }

    @Test
    @DisplayName("Board Repo - unyummy 테스트")
    void deleteYummy() {
        //given
        Yummy yummy = Yummy.createYummy(member.getId(), board.getId());

        yummy = yummyRepository.save(yummy);

        //when
        yummyRepository.delete(yummy);

        //then
        assertFalse(yummyRepository.existsByMemberIdAndBoardId(yummy));
    }

    @Test
    @DisplayName("Board Repo - yummy 유무 확인 테스트")
    void existsYummyByMemberIdAndBoardId() {
        //given
        Yummy yummy = Yummy.createYummy(member.getId(), board.getId());

        yummy = yummyRepository.save(yummy);

        //when & then
        assertTrue(yummyRepository.existsByMemberIdAndBoardId(yummy));
    }
}
