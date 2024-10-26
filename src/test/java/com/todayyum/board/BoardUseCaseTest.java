package com.todayyum.board;

import com.todayyum.board.application.*;
import com.todayyum.board.application.repository.*;
import com.todayyum.board.domain.*;
import com.todayyum.board.dto.request.*;
import com.todayyum.board.dto.response.BoardDetailResponse;
import com.todayyum.board.dto.response.BoardListResponse;
import com.todayyum.board.dto.response.CommentListResponse;
import com.todayyum.global.util.S3Util;
import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Member;
import com.todayyum.member.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
public class BoardUseCaseTest {

    @Mock
    private BoardRepository boardRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private BoardTagRepository boardTagRepository;
    @Mock
    private BoardImageRepository boardImageRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private YummyRepository yummyRepository;
    @Mock
    private S3Util s3Util;
    @InjectMocks
    private AddBoardUseCase addBoardUseCase;
    @InjectMocks
    private ModifyBoardUseCase modifyBoardUseCase;
    @InjectMocks
    private FindBoardUseCase findBoardUseCase;
    @InjectMocks
    private RemoveBoardUseCase removeBoardUseCase;
    @InjectMocks
    private AddYummyUseCase addYummyUseCase;
    @InjectMocks
    private RemoveYummyUseCase removeYummyUseCase;
    @InjectMocks
    private AddCommentUseCase addCommentUseCase;
    @InjectMocks
    private ModifyCommentUseCase modifyCommentUseCase;
    @InjectMocks
    private FindCommentUseCase findCommentUseCase;
    @InjectMocks
    private RemoveCommentUseCase removeCommentUseCase;
    @Mock
    private ApplicationContext context;

    @BeforeEach
    void setUp() {
        addBoardUseCase.setApplicationContext(context);
    }

    @Test
    @DisplayName("Board UC - 게시물 등록 테스트")
    void addBoard() throws Exception {
        //given
        BoardAddRequest boardAddRequest = BoardAddRequest.builder()
                .content("음 맛있다~")
                .category(Category.KOREAN_FOOD)
                .mealTime(MealTime.BREAKFAST)
                .tasteScore(3)
                .moodScore(3)
                .priceScore(3)
                .totalScore(3D)
                .ateAt(LocalDate.now(ZoneId.of("Asia/Seoul")))
                .build();

        Board board = Board.createBoard(boardAddRequest);
        Long id = 100000L;
        board.changeId(id);

        when(boardRepository.save(any(Board.class)))
                .thenReturn(board);

        //when
        Long boardId = addBoardUseCase.addBoard(boardAddRequest);

        //then
        assertEquals(boardId, id);
        verify(boardRepository, times(1)).save(any(Board.class));
    }

    @Test
    @DisplayName("Board UC - 태그 등록 테스트")
    void addBoardTag() throws Exception {
        //given
        Long boardId = 100000L;
        Long tagId = 100000L;

        String content = "가성비";

        Tag tag = Tag.createTag(content);
        tag.changeId(tagId);

        List<String> tags = new ArrayList<>();
        tags.add(content);

        when(tagRepository.findByContent(any(String.class)))
                .thenReturn(tag);

        doNothing()
                .when(boardTagRepository)
                .save(tagId, boardId);

        //when
        addBoardUseCase.addBoardTag(boardId, tags);

        //then
        verify(tagRepository, times(1)).findByContent(any(String.class));
    }

    @Test
    @DisplayName("Board UC - 유저가 작성한 게시물 조회 테스트")
    void listBoardByMember() throws Exception {
        //given
        Long boardId = 100000L;
        UUID memberId = UUID.randomUUID();

        List<BoardListResponse> boardListResponseList = new ArrayList<>();
        BoardListResponse boardListResponse = new BoardListResponse(
                boardId, 0D, 3L, Category.KOREAN_FOOD);
        boardListResponseList.add(boardListResponse);

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<BoardListResponse> boardListResponses = new PageImpl<>(boardListResponseList, pageRequest, 1);

        Pageable pageable = Pageable.ofSize(10);

        when(boardRepository.findByMemberId(any(Pageable.class), eq(memberId)))
                .thenReturn(boardListResponses);

        //when
        boardListResponses = findBoardUseCase.listBoardByMember(pageable, memberId);

        //then
        assertEquals(boardListResponse.getId(), boardListResponses.getContent().get(0).getId());

        verify(boardRepository, times(1)).findByMemberId(any(Pageable.class), any(UUID.class));
    }

    @Test
    @DisplayName("Board UC - 유저가 yummy한 게시글 목록 조회 테스트")
    void listBoardByMemberAndYummy() throws Exception {
        //given
        Long boardId = 100000L;
        UUID memberId = UUID.randomUUID();

        List<BoardListResponse> boardListResponseList = new ArrayList<>();
        BoardListResponse boardListResponse = new BoardListResponse(
                boardId, 0D, 3L, Category.KOREAN_FOOD);
        boardListResponseList.add(boardListResponse);

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<BoardListResponse> boardListResponses = new PageImpl<>(boardListResponseList, pageRequest, 1);

        Pageable pageable = Pageable.ofSize(10);

        when(boardRepository.findByMemberIdAndYummy(any(Pageable.class), eq(memberId)))
                .thenReturn(boardListResponses);

        //when
        boardListResponses = findBoardUseCase.listBoardByMemberAndYummy(pageable, memberId);

        //then
        assertEquals(boardListResponse.getId(), boardListResponses.getContent().get(0).getId());

        verify(boardRepository, times(1))
                .findByMemberIdAndYummy(any(Pageable.class), any(UUID.class));
    }

    @Test
    @DisplayName("Board UC - 게시글 목록 조회 테스트")
    void listBoard() throws Exception {
        //given
        Long boardId = 100000L;

        BoardSearchRequest boardSearchRequest = BoardSearchRequest.builder()
                .sortBy("yummy")
                .build();

        List<BoardListResponse> boardListResponseList = new ArrayList<>();
        BoardListResponse boardListResponse = new BoardListResponse(
                boardId, 0D, 3L, Category.KOREAN_FOOD);
        boardListResponseList.add(boardListResponse);

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<BoardListResponse> boardListResponses = new PageImpl<>(boardListResponseList, pageRequest, 1);

        Pageable pageable = Pageable.ofSize(10);

        when(boardRepository.findList(any(Pageable.class), any(BoardSearchRequest.class)))
                .thenReturn(boardListResponses);

        //when
        boardListResponses = findBoardUseCase.listBoard(pageable, boardSearchRequest);

        //then
        assertEquals(boardListResponse.getId(), boardListResponses.getContent().get(0).getId());

        verify(boardRepository, times(1))
                .findList(any(Pageable.class), any(BoardSearchRequest.class));
    }

    @Test
    @DisplayName("Board UC - 게시글 조회 테스트")
    void findBoard() throws Exception {
        //given
        Long boardId = 100000L;
        UUID memberId = UUID.randomUUID();

        Member member = Member.builder()
                .id(memberId)
                .email("test@test.com")
                .nickname("yonggkim")
                .role(Role.USER)
                .build();

        Board board = Board.builder()
                .id(boardId)
                .memberId(memberId)
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

        when(boardRepository.findById(boardId))
                .thenReturn(board);

        when(memberRepository.findById(memberId))
                .thenReturn(member);

        //when
        BoardDetailResponse boardDetailResponse = findBoardUseCase.findBoard(memberId, boardId);

        //then
        assertEquals(boardId, boardDetailResponse.id);

        verify(boardRepository, times(1))
                .findById(any(Long.class));
    }

    @Test
    @DisplayName("Board UC - 오늘의 얌 조회 테스트")
    void boardByYummy() throws Exception {
        //given
        Long boardId = 100000L;

        List<BoardListResponse> boardListResponseList = new ArrayList<>();
        BoardListResponse boardListResponse = new BoardListResponse(
                boardId, 0D, 3L, Category.KOREAN_FOOD);
        boardListResponseList.add(boardListResponse);

        when(boardRepository.findTopByYummy())
                .thenReturn(boardListResponseList);

        //when
        boardListResponseList = findBoardUseCase.boardByYummy();

        //then
        assertEquals(boardListResponse.getId(), boardListResponseList.get(0).getId());

        verify(boardRepository, times(1))
                .findTopByYummy();
    }

    @Test
    @DisplayName("Board UC - 오늘의 얌 목록 조회 테스트")
    void listBoardByYummy() throws Exception {
        //given
        Long boardId = 100000L;

        List<BoardListResponse> boardListResponseList = new ArrayList<>();
        BoardListResponse boardListResponse = new BoardListResponse(
                boardId, 0D, 3L, Category.KOREAN_FOOD);
        boardListResponseList.add(boardListResponse);

        when(boardRepository.findTopListByYummy())
                .thenReturn(boardListResponseList);

        //when
        boardListResponseList = findBoardUseCase.listBoardByYummy();

        //then
        assertEquals(boardListResponse.getId(), boardListResponseList.get(0).getId());

        verify(boardRepository, times(1))
                .findTopListByYummy();
    }

    @Test
    @DisplayName("Board UC - 게시글 태그 검색 테스트")
    void listBoardByTag() throws Exception {
        //given
        Long boardId = 100000L;
        String tag = "가성비";

        List<BoardListResponse> boardListResponseList = new ArrayList<>();
        BoardListResponse boardListResponse = new BoardListResponse(
                boardId, 0D, 3L, Category.KOREAN_FOOD);
        boardListResponseList.add(boardListResponse);

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<BoardListResponse> boardListResponses = new PageImpl<>(boardListResponseList, pageRequest, 1);

        Pageable pageable = Pageable.ofSize(10);

        when(boardRepository.findListByTag(any(Pageable.class), eq(tag)))
                .thenReturn(boardListResponses);

        //when
        boardListResponses = findBoardUseCase.listBoardByTag(pageable, tag);

        //then
        assertEquals(boardListResponse.getId(), boardListResponses.getContent().get(0).getId());

        verify(boardRepository, times(1))
                .findListByTag(any(Pageable.class), any(String.class));
    }

    @Test
    @DisplayName("Board UC - 게시글 수정 테스트")
    void modifyBoard() throws Exception {
        //given
        Long boardId = 100000L;
        UUID memberId = UUID.randomUUID();

        Board board = Board.builder()
                .id(boardId)
                .memberId(memberId)
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

        BoardModifyRequest boardModifyRequest = BoardModifyRequest.builder()
                .id(boardId)
                .memberId(memberId)
                .mealTime(MealTime.LUNCH)
                .ateAt(LocalDate.now(ZoneId.of("Asia/Seoul")))
                .totalScore(3D)
                .moodScore(3)
                .tasteScore(3)
                .priceScore(3)
                .category(Category.JAPANESE_FOOD)
                .content("호구마")
                .build();

        when(boardRepository.findById(boardId))
                .thenReturn(board);

        when(boardRepository.save(any(Board.class)))
                .thenReturn(board);

        //when
        modifyBoardUseCase.modifyBoard(boardModifyRequest);

        //then
        assertEquals(boardModifyRequest.getContent(), board.getContent());

        verify(boardRepository, times(1))
                .save(any(Board.class));
    }

    @Test
    @DisplayName("Board UC - 게시글 삭제 테스트")
    void removeBoard() throws Exception {
        //given
        Long boardId = 100000L;
        UUID memberId = UUID.randomUUID();

        Board board = Board.builder()
                .id(boardId)
                .memberId(memberId)
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

        when(boardRepository.findById(boardId))
                .thenReturn(board);

        //when
        removeBoardUseCase.removeBoard(memberId, boardId);

        //then
        verify(boardRepository, times(1))
                .deleteById(any(Long.class));
    }

    @Test
    @DisplayName("Board UC - 댓글 등록 테스트")
    void addComment() throws Exception {
        //given
        Long boardId = 100000L;
        UUID memberId = UUID.randomUUID();

        CommentAddRequest commentAddRequest = CommentAddRequest.builder()
                .memberId(memberId)
                .boardId(boardId)
                .content("맛있당~")
                .build();

        Comment comment = Comment.createComment(commentAddRequest);
        Long commentId = 100000L;
        comment.changeId(commentId);

        when(commentRepository.save(any(Comment.class)))
                .thenReturn(comment);

        //when
        Long savedId = addCommentUseCase.addComment(commentAddRequest);

        //then
        assertEquals(commentId, savedId);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("Board UC - 댓글 목록 조회 테스트")
    void listComment() throws Exception {
        //given
        Long boardId = 100000L;
        UUID memberId = UUID.randomUUID();

        List<CommentListResponse> commentListResponseList = new ArrayList<>();
        CommentListResponse commentListResponse = CommentListResponse.builder()
                .content("맛있당")
                .id(100000L)
                .boardId(boardId)
                .memberId(memberId)
                .nickname("yonggkim")
                .modifiedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
        commentListResponseList.add(commentListResponse);

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<CommentListResponse> commentListResponses = new PageImpl<>(commentListResponseList, pageRequest, 1);

        Pageable pageable = Pageable.ofSize(10);

        when(commentRepository.findByBoardId(eq(boardId), any(Pageable.class)))
                .thenReturn(commentListResponses);

        //when
        commentListResponses = findCommentUseCase.listComment(boardId, pageable);

        //then
        assertEquals(commentListResponse.getId(), commentListResponses.getContent().get(0).getId());

        verify(commentRepository, times(1))
                .findByBoardId(any(Long.class), any(Pageable.class));
    }

    @Test
    @DisplayName("Board UC - 댓글 수정 테스트")
    void modifyComment() throws Exception {
        //given
        Long boardId = 100000L;
        UUID memberId = UUID.randomUUID();
        Long id = 100000L;

        Comment comment = Comment.builder()
                .boardId(boardId)
                .memberId(memberId)
                .content("맛있당")
                .modifiedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .id(id)
                .build();

        CommentModifyRequest commentModifyRequest = CommentModifyRequest.builder()
                .memberId(memberId)
                .content("맛없당")
                .id(id)
                .build();

        when(commentRepository.findById(id))
                .thenReturn(comment);

        when(commentRepository.save(any(Comment.class)))
                .thenReturn(comment);

        //when
        Long savedId = modifyCommentUseCase.modifyComment(commentModifyRequest);

        //then
        assertEquals(commentModifyRequest.getContent(), comment.getContent());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("Board UC - 댓글 삭제 테스트")
    void removeComment() throws Exception {
        //given
        Long boardId = 100000L;
        UUID memberId = UUID.randomUUID();
        Long id = 100000L;

        Comment comment = Comment.builder()
                .boardId(boardId)
                .memberId(memberId)
                .content("맛있당")
                .id(id)
                .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .modifiedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();

        when(commentRepository.findById(id))
                .thenReturn(comment);

        //when
        removeCommentUseCase.removeComment(id, memberId);

        //then
        verify(commentRepository, times(1))
                .delete(any(Comment.class));
    }

    @Test
    @DisplayName("Board UC - yummy 테스트")
    void addYummy() throws Exception {
        //given
        UUID memberId = UUID.randomUUID();
        Long boardId = 100000L;
        Long id = 100000L;

        Board board = Board.builder()
                .id(boardId)
                .memberId(memberId)
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

        Yummy yummy = Yummy.createYummy(memberId, boardId);

        yummy.changeId(id);

        when(yummyRepository.existsByMemberIdAndBoardId(any(Yummy.class)))
                .thenReturn(false);

        when(yummyRepository.save(any(Yummy.class)))
                .thenReturn(yummy);

        when(boardRepository.findById(boardId))
                .thenReturn(board);

        //when
        Long savedId = addYummyUseCase.addYummy(memberId, boardId);

        //then
        assertEquals(id, savedId);
        verify(yummyRepository, times(1)).save(any(Yummy.class));
    }

    @Test
    @DisplayName("Board UC - unyummy 테스트")
    void removeYummy() throws Exception {
        //given
        UUID memberId = UUID.randomUUID();
        Long boardId = 100000L;
        Long id = 100000L;

        Board board = Board.builder()
                .id(boardId)
                .memberId(memberId)
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

        Yummy yummy = Yummy.createYummy(memberId, boardId);

        yummy.changeId(id);

        when(yummyRepository.existsByMemberIdAndBoardId(any(Yummy.class)))
                .thenReturn(true);

        when(boardRepository.findById(boardId))
                .thenReturn(board);

        doNothing()
                .when(yummyRepository).delete(any(Yummy.class));

        //when
        removeYummyUseCase.removeYummy(memberId, boardId);

        //then
        verify(yummyRepository, times(1)).delete(any(Yummy.class));
    }
}
