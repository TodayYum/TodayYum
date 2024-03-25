package com.todayyum.board.controller;

import com.todayyum.auth.userDetails.CustomUserDetails;
import com.todayyum.board.application.AddBoardUseCase;
import com.todayyum.board.application.FindBoardUseCase;
import com.todayyum.board.dto.request.BoardAddRequest;
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

    @GetMapping
    public ResponseEntity<?> boardList() {
        return null;
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<?> boardDetail(@PathVariable Long boardId) {
        return null;
    }

    @PostMapping("/{boardId}")
    public ResponseEntity<?> boardModify(@PathVariable Long boardId) {
        return null;
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> boardRemove(@PathVariable Long boardId) {
        return null;
    }

    @PostMapping("/{boardId}/comments")
    public ResponseEntity<?> commentAdd(@PathVariable Long boardId) {
        return null;
    }

    @PatchMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<?> commentModify(
            @PathVariable Long boardId, @PathVariable Long commentId) {
        return null;
    }

    @DeleteMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<?> commentRemove(
            @PathVariable Long boardId, @PathVariable Long commentId) {
        return null;
    }

    @GetMapping("/{boardId}/comments")
    public ResponseEntity<?> commentList(@PathVariable Long boardId) {
        return null;
    }

    @PostMapping("/{boardId}/yummys")
    public ResponseEntity<?> yummyAdd(@PathVariable Long boardId) {
        return null;
    }

    @DeleteMapping("/{boardId}/yummys")
    public ResponseEntity<?> yummyRemove(@PathVariable Long boardId) {
        return null;
    }

}
