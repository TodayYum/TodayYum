package com.todayyum.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todayyum.board.application.*;
import com.todayyum.board.controller.BoardController;
import com.todayyum.board.domain.Category;
import com.todayyum.board.dto.request.*;
import com.todayyum.board.dto.response.BoardDetailResponse;
import com.todayyum.board.dto.response.BoardListResponse;
import com.todayyum.board.dto.response.CommentListResponse;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import com.todayyum.member.WithMockMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoardController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockMember
public class BoardControllerTest {

    @MockBean
    private AddBoardUseCase addBoardUseCase;
    @MockBean
    private FindBoardUseCase findBoardUseCase;
    @MockBean
    private ModifyBoardUseCase modifyBoardUseCase;
    @MockBean
    private RemoveBoardUseCase removeBoardUseCase;
    @MockBean
    private AddCommentUseCase addCommentUseCase;
    @MockBean
    private FindCommentUseCase findCommentUseCase;
    @MockBean
    private ModifyCommentUseCase modifyCommentUseCase;
    @MockBean
    private RemoveCommentUseCase removeCommentUseCase;
    @MockBean
    private AddYummyUseCase addYummyUseCase;
    @MockBean
    private RemoveYummyUseCase removeYummyUseCase;
    @MockBean
    private JpaMetamodelMappingContext jpaMappingContext;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Board Cont - 게시물 등록 테스트")
    void addBoard() throws Exception {
        //given
        Long boardId = 100000L;
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("content", "음 맛있다~");
        formData.add("category", "KOREAN_FOOD");
        formData.add("mealTime", "BREAKFAST");
        formData.add("tasteScore", "3");
        formData.add("priceScore", "3");
        formData.add("moodScore", "3");
        formData.add("totalScore", "3");
        formData.add("ateAt", "2024-04-23");

        when(addBoardUseCase.addBoard(any(BoardAddRequest.class)))
                .thenReturn(boardId);

        //when
        ResultActions resultActions = mockMvc.perform(
                multipart("/api/boards")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(formData));

        //then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.result").value(boardId))
                .andDo(print());
    }

    @Test
    @DisplayName("Board Cont - 게시물 등록 실패 테스트(입력 오류)")
    void addBoardFailByInput() throws Exception {
        //given
        Long boardId = 100000L;
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        //when
        ResultActions resultActions = mockMvc.perform(
                multipart("/api/boards")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(formData));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("Board Cont - 유저가 작성한 게시물 조회 테스트")
    void listBoardByMemberId() throws Exception {
        //given
        Long boardId = 100000L;
        UUID memberId = UUID.randomUUID();

        List<BoardListResponse> boardListResponseList = new ArrayList<>();
        BoardListResponse boardListResponse = new BoardListResponse(
                boardId, 0D, 3L, Category.KOREAN_FOOD);
        boardListResponseList.add(boardListResponse);

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<BoardListResponse> boardListResponses = new PageImpl<>(boardListResponseList, pageRequest, 1);

        when(findBoardUseCase.listBoardByMember(any(Pageable.class), eq(memberId)))
                .thenReturn(boardListResponses);

        //when
        ResultActions resultActions = mockMvc.perform(
                get("/api/boards/members/{memberId}", memberId)
                        .param("page", "0"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.content[0].id")
                        .value(boardId))
                .andDo(print());
    }

    @Test
    @DisplayName("Board Cont - 유저가 작성한 게시물 조회 실패 테스트(멤버 식별자 오류)")
    void listBoardByMemberIdFailByMemberId() throws Exception {
        //given
        when(findBoardUseCase.listBoardByMember(any(Pageable.class), any(UUID.class)))
                .thenThrow(new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        //when
        ResultActions resultActions = mockMvc.perform(
                get("/api/boards/members/{memberId}", UUID.randomUUID())
                        .param("page", "0"));

        //then
        resultActions.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.MEMBER_ID_NOT_FOUND.getMessage()))
                .andDo(print());
    }

    @Test
    @DisplayName("Board Cont - 게시물 삭제 테스트")
    void removeBoard() throws Exception {
        //given
        doNothing()
                .when(removeBoardUseCase)
                .removeBoard(any(UUID.class), any(Long.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/boards/{boardId}", 100000L));

        //then
        resultActions.andExpect(
                        status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.OK.getMessage()));
    }

    @Test
    @DisplayName("Board Cont - 댓글 등록 테스트")
    void addComment() throws Exception {
        //given
        Long boardId = 100000L;
        Long commentId = 100000L;

        CommentAddRequest commentAddRequest = CommentAddRequest.builder()
                .content("음 맛있다~")
                .build();

        when(addCommentUseCase.addComment(any(CommentAddRequest.class)))
                .thenReturn(commentId);

        //when
        ResultActions resultActions = mockMvc.perform(
                multipart("/api/boards/{boardId}/comments", boardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commentAddRequest)));

        //then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.result").value(commentId))
                .andDo(print());
    }

    @Test
    @DisplayName("Board Cont - 댓글 등록 실패 테스트(입력 오류)")
    void addCommentFailByInput() throws Exception {
        //given
        Long boardId = 100000L;
        CommentAddRequest commentAddRequest = CommentAddRequest.builder().build();

        //when
        ResultActions resultActions = mockMvc.perform(
                multipart("/api/boards/{boardId}/comments", boardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commentAddRequest)));

        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message").value("내용을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("Board Cont - 댓글 수정 테스트")
    void modifyComment() throws Exception {
        //given
        Long commentId = 100000L;

        CommentModifyRequest commentModifyRequest = CommentModifyRequest.builder()
                .content("호구마 맛있다.")
                .build();

        when(modifyCommentUseCase.modifyComment(any(CommentModifyRequest.class)))
                .thenReturn(commentId);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/boards/comments/{commentId}", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commentModifyRequest)));

        //then
        resultActions.andExpect(
                status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.OK.getMessage()));
    }

    @Test
    @DisplayName("Board Cont - 댓글 수정 실패 테스트(입력 오류)")
    void modifyCommentFailByMemberId() throws Exception {
        //given
        Long commentId = 100000L;

        CommentModifyRequest commentModifyRequest = CommentModifyRequest.builder()
                .build();

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/boards/comments/{commentId}", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commentModifyRequest)));

        //then
        resultActions.andExpect(
                        status().isBadRequest())
                .andExpect(jsonPath("$[0].message")
                        .value("내용을 입력해주세요."));
    }

    @Test
    @DisplayName("Board Cont - 댓글 목록 조회 테스트")
    void listComment() throws Exception {
        //given
        Long boardId = 100000L;
        UUID memberId = UUID.randomUUID();

        List<CommentListResponse> commentListResponseList = new ArrayList<>();
        CommentListResponse commentListResponse = CommentListResponse.builder()
                .boardId(boardId)
                .memberId(memberId)
                .content("맛있당")
                .nickname("yonggkim")
                .profile("image.jpg")
                .id(10000L)
                .modifiedAt(LocalDateTime.now())
                .build();
        commentListResponseList.add(commentListResponse);

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<CommentListResponse> commentListResponses = new PageImpl<>(commentListResponseList, pageRequest, 1);

        when(findCommentUseCase.listComment(any(Long.class), any(Pageable.class)))
                .thenReturn(commentListResponses);

        //when
        ResultActions resultActions = mockMvc.perform(
                get("/api/boards/{boardId}/comments", boardId)
                        .param("page", "0"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.content[0].id")
                        .value(10000L))
                .andDo(print());
    }

    @Test
    @DisplayName("Board Cont - 댓글 삭제 테스트")
    void removeComment() throws Exception {
        //given
        doNothing()
                .when(removeCommentUseCase)
                .removeComment(any(Long.class), any(UUID.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/boards/comments/{boardId}", 100000L));

        //then
        resultActions.andExpect(
                        status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.OK.getMessage()));
    }

    @Test
    @DisplayName("Board Cont - yummy 테스트")
    void addYummy() throws Exception {
        //given
        Long id = 100000L;

        when(addYummyUseCase.addYummy(any(UUID.class), any(Long.class)))
                .thenReturn(id);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/boards/{boardId}/yummys", 100000L));

        //then
        resultActions.andExpect(
                        status().isCreated())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.CREATED.getMessage()))
                .andExpect(jsonPath("$.result")
                        .value(id));
    }

    @Test
    @DisplayName("Board Cont - yummy 실패 테스트(중복 오류)")
    void addYummyFailByDuplication() throws Exception {
        //given
        when(addYummyUseCase.addYummy(any(UUID.class), any(Long.class)))
                .thenThrow(new CustomException(ResponseCode.DUPLICATE_YUMMY));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/boards/{boardId}/yummys", 100000L));

        //then
        resultActions.andExpect(
                        status().isConflict())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.DUPLICATE_YUMMY.getMessage()));
    }


    @Test
    @DisplayName("Board Cont - unyummy 테스트")
    void removeYummy() throws Exception {
        //given
        doNothing()
                .when(removeYummyUseCase)
                .removeYummy(any(UUID.class), any(Long.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/boards/{boardId}/yummys", 100000L));

        //then
        resultActions.andExpect(
                        status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.OK.getMessage()));
    }

    @Test
    @DisplayName("Board Cont - unyummy 실패 테스트(yummy X 오류)")
    void removeYummyFailByNotYummy() throws Exception {
        //given
        doThrow(new CustomException(ResponseCode.NOT_YUMMY))
                .when(removeYummyUseCase)
                .removeYummy(any(UUID.class), any(Long.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/boards/{boardId}/yummys", 100000L));

        //then
        resultActions.andExpect(
                        status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.NOT_YUMMY.getMessage()));
    }

    @Test
    @DisplayName("Board Cont - 게시글 조회 테스트")
    void findBoard() throws Exception {
        //given
        Long boardId = 100000L;
        UUID memberId = UUID.randomUUID();

        BoardDetailResponse boardDetailResponse = BoardDetailResponse.builder()
                .id(boardId)
                .memberId(memberId)
                .content("음~ 맛있다.")
                .category("KOREAN_FOOD")
                .priceScore(3)
                .moodScore(3)
                .tasteScore(3)
                .totalScore(3D)
                .yummyCount(0L)
                .ateAt(LocalDate.now())
                .mealTime("LUNCH")
                .nickname("test")
                .build();

        when(findBoardUseCase.findBoard(any(UUID.class), any(Long.class)))
                .thenReturn(boardDetailResponse);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/boards/{boardId}", boardId));

        //then
        resultActions.andExpect(
                        status().isOk())
                .andExpect(jsonPath("$.result.id")
                        .value(boardId))
                .andExpect(jsonPath("$.result.memberId")
                        .value(memberId.toString()))
                .andDo(print());
    }

    @Test
    @DisplayName("Board Cont - 게시글 조회 실패 테스트(게시글 식별자 오류)")
    void findBoardFailByBoardId() throws Exception {
        //given
        Long boardId = 100000L;

        when(findBoardUseCase.findBoard(any(UUID.class), any(Long.class)))
                .thenThrow(new CustomException(ResponseCode.BOARD_ID_NOT_FOUND));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/boards/{boardId}", boardId));

        //then
        resultActions.andExpect(
                        status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.BOARD_ID_NOT_FOUND.getMessage()))
                .andDo(print());
    }

    @Test
    @DisplayName("Board Cont - 게시글 수정 테스트")
    void modifyBoard() throws Exception {
        //given
        Long boardId = 100000L;

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("content", "음 맛있다~");
        formData.add("category", "KOREAN_FOOD");
        formData.add("mealTime", "BREAKFAST");
        formData.add("tasteScore", "3");
        formData.add("priceScore", "3");
        formData.add("moodScore", "3");
        formData.add("totalScore", "3");
        formData.add("ateAt", "2024-04-23");

        when(modifyBoardUseCase.modifyBoard(any(BoardModifyRequest.class)))
                .thenReturn(boardId);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/boards/{boardId}", boardId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(formData));

        //then
        resultActions.andExpect(
                status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(ResponseCode.OK.getMessage()));
    }

    @Test
    @DisplayName("Board Cont - 게시글 수정 실패 테스트(입력 오류)")
    void modifyBoardFailByInput() throws Exception {
        //given
        Long boardId = 100000L;

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        when(modifyBoardUseCase.modifyBoard(any(BoardModifyRequest.class)))
                .thenReturn(boardId);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/boards/{boardId}", boardId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(formData));

        //then
        resultActions.andExpect(
                        status().isBadRequest());
    }

    @Test
    @DisplayName("Board Cont - 게시글 목록 조회 테스트")
    void boardList() throws Exception {
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

        when(findBoardUseCase.listBoard(any(Pageable.class), any(BoardSearchRequest.class)))
                .thenReturn(boardListResponses);

        //when
        ResultActions resultActions = mockMvc.perform(
                get("/api/boards")
                        .param("page", "0")
                        .param("sortBy", "yummy"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.content[0].id")
                        .value(boardId))
                .andDo(print());
    }

    @Test
    @DisplayName("Board Cont - 게시글 태그 검색 테스트")
    void boardSearchByTag() throws Exception {
        //given
        Long boardId = 100000L;
        UUID memberId = UUID.randomUUID();

        List<BoardListResponse> boardListResponseList = new ArrayList<>();
        BoardListResponse boardListResponse = new BoardListResponse(
                boardId, 0D, 3L, Category.KOREAN_FOOD);
        boardListResponse.changeTag("가성비");
        boardListResponseList.add(boardListResponse);

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<BoardListResponse> boardListResponses = new PageImpl<>(boardListResponseList, pageRequest, 1);

        when(findBoardUseCase.listBoardByTag(any(Pageable.class), eq("가성")))
                .thenReturn(boardListResponses);

        //when
        ResultActions resultActions = mockMvc.perform(
                get("/api/boards/search")
                        .param("page", "0")
                        .param("content", "가성"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.content[0].id")
                        .value(boardId))
                .andDo(print());
    }

    @Test
    @DisplayName("Board Cont - 오늘의 얌 목록 조회 테스트")
    void boardListByYummy() throws Exception {
        //given
        Long boardId = 100000L;
        UUID memberId = UUID.randomUUID();

        List<BoardListResponse> boardListResponseList = new ArrayList<>();
        BoardListResponse boardListResponse = new BoardListResponse(
                boardId, 0D, 3L, Category.KOREAN_FOOD);
        boardListResponseList.add(boardListResponse);

        when(findBoardUseCase.listBoardByYummy())
                .thenReturn(boardListResponseList);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/boards/yummys"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.result[0].id")
                        .value(boardId))
                .andDo(print());
    }

    @Test
    @DisplayName("Board Cont - 오늘의 얌 조회 테스트")
    void boardByYummy() throws Exception {
        //given
        Long boardId = 100000L;
        UUID memberId = UUID.randomUUID();

        List<BoardListResponse> boardListResponseList = new ArrayList<>();
        BoardListResponse boardListResponse = new BoardListResponse(
                boardId, 0D, 3L, Category.KOREAN_FOOD);
        boardListResponseList.add(boardListResponse);

        when(findBoardUseCase.boardByYummy())
                .thenReturn(boardListResponseList);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/boards/yummys/top"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.result[0].id")
                        .value(boardId))
                .andDo(print());
    }

    @Test
    @DisplayName("Board Cont - 유저가 yummy한 게시글 목록 조회 테스트")
    void boardListByMemberIdAndYummy() throws Exception {
        //given
        Long boardId = 100000L;
        UUID memberId = UUID.randomUUID();

        List<BoardListResponse> boardListResponseList = new ArrayList<>();
        BoardListResponse boardListResponse = new BoardListResponse(
                boardId, 0D, 3L, Category.KOREAN_FOOD);
        boardListResponseList.add(boardListResponse);

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<BoardListResponse> boardListResponses = new PageImpl<>(boardListResponseList, pageRequest, 1);

        when(findBoardUseCase.listBoardByMemberAndYummy(any(Pageable.class), eq(memberId)))
                .thenReturn(boardListResponses);

        //when
        ResultActions resultActions = mockMvc.perform(
                get("/api/boards/members/{memberId}/yummys", memberId)
                        .param("page", "0"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.content[0].id")
                        .value(boardId))
                .andDo(print());
    }
}
