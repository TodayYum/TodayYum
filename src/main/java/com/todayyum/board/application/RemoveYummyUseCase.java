package com.todayyum.board.application;

import com.todayyum.board.application.repository.YummyRepository;
import com.todayyum.board.domain.Yummy;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RemoveYummyUseCase {
    private final YummyRepository yummyRepository;

    @Transactional
    public void removeYummy(UUID memberId, Long boardId) {
        Yummy yummy = Yummy.createYummy(memberId, boardId);

        if(!yummyRepository.existsByMemberIdAndBoardId(yummy)) {
            throw new CustomException(ResponseCode.NOT_YUMMY);
        }

        yummyRepository.delete(yummy);
    }

}
