package com.todayyum.board.controller;

import com.todayyum.auth.userDetails.CustomUserDetails;
import com.todayyum.board.application.*;
import com.todayyum.board.dto.request.BoardAddRequest;
import com.todayyum.board.dto.request.BoardModifyRequest;
import com.todayyum.board.dto.request.CommentAddRequest;
import com.todayyum.board.dto.request.CommentModifyRequest;
import com.todayyum.global.dto.response.BaseResponse;
import com.todayyum.global.dto.response.ResponseCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
@Slf4j
public class BoardController {

    private final AddBoardUseCase addBoardUseCase;
    private final FindBoardUseCase findBoardUseCase;
    private final ModifyBoardUseCase modifyBoardUseCase;
    private final RemoveBoardUseCase removeBoardUseCase;
    private final AddCommentUseCase addCommentUseCase;
    private final FindCommentUseCase findCommentUseCase;
    private final ModifyCommentUseCase modifyCommentUseCase;
    private final RemoveCommentUseCase removeCommentUseCase;
    private final AddYummyUseCase addYummyUseCase;
    private final RemoveYummyUseCase removeYummyUseCase;

    @PostMapping
    public ResponseEntity<?> boardAdd(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                      @Valid BoardAddRequest boardAddRequest) {
        boardAddRequest.setMemberId(customUserDetails.getMemberId());

        return BaseResponse.createResponseEntity(ResponseCode.CREATED,
                addBoardUseCase.addBoard(boardAddRequest));
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<?> boardListByMemberId(@PathVariable UUID memberId) {
        return BaseResponse.createResponseEntity(ResponseCode.OK,
                findBoardUseCase.listBoardByMember(memberId));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> boardRemove(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                         @PathVariable Long boardId) {
        removeBoardUseCase.removeBoard(customUserDetails.getMemberId(), boardId);

        return BaseResponse.createResponseEntity(ResponseCode.OK);
    }

    @PostMapping("/{boardId}/comments")
    public ResponseEntity<?> commentAdd(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                        @PathVariable Long boardId,
                                        @Valid CommentAddRequest commentAddRequest) {
        commentAddRequest.setBoardId(boardId);
        commentAddRequest.setMemberId(customUserDetails.getMemberId());

        return BaseResponse.createResponseEntity(ResponseCode.CREATED,
                addCommentUseCase.addComment(commentAddRequest));
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<?> commentModify(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                           @PathVariable Long commentId,
                                           @Valid CommentModifyRequest commentModifyRequest) {
        commentModifyRequest.setId(commentId);
        commentModifyRequest.setMemberId(customUserDetails.getMemberId());

        return BaseResponse.createResponseEntity(ResponseCode.OK,
                modifyCommentUseCase.modifyComment(commentModifyRequest));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> commentRemove(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                           @PathVariable Long commentId) {
        removeCommentUseCase.removeComment(commentId, customUserDetails.getMemberId());

        return BaseResponse.createResponseEntity(ResponseCode.OK);
    }

    @GetMapping("/{boardId}/comments")
    public ResponseEntity<?> commentList(@PathVariable Long boardId) {
        return BaseResponse.createResponseEntity(ResponseCode.OK, findCommentUseCase.listComment(boardId));
    }

    @PostMapping("/{boardId}/yummys")
    public ResponseEntity<?> yummyAdd(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                      @PathVariable Long boardId) {
        return BaseResponse.createResponseEntity(ResponseCode.CREATED,
                addYummyUseCase.addYummy(customUserDetails.getMemberId(), boardId));
    }

    @DeleteMapping("/{boardId}/yummys")
    public ResponseEntity<?> yummyRemove(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                         @PathVariable Long boardId) {
        removeYummyUseCase.removeYummy(customUserDetails.getMemberId(), boardId);

        return BaseResponse.createResponseEntity(ResponseCode.OK);
    }

    @GetMapping
    public ResponseEntity<?> boardList() {
        return null;
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<?> boardDetail(@PathVariable Long boardId) {
        return BaseResponse.createResponseEntity(ResponseCode.OK, findBoardUseCase.detailBoard(boardId));
    }

    @PostMapping("/{boardId}")
    public ResponseEntity<?> boardModify(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                         @PathVariable Long boardId,
                                         @Valid BoardModifyRequest boardModifyRequest) {
        boardModifyRequest.setId(boardId);
        boardModifyRequest.setMemberId(customUserDetails.getMemberId());

        return BaseResponse.createResponseEntity(ResponseCode.OK, modifyBoardUseCase.modifyBoard(boardModifyRequest));
    }

}
