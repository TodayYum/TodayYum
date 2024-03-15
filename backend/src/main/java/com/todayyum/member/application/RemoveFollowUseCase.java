package com.todayyum.member.application;

import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import com.todayyum.member.application.repository.FollowRepository;
import com.todayyum.member.domain.Follow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RemoveFollowUseCase {

    private final FollowRepository followRepository;

    @Transactional
    public void removeFollow(UUID fromMemberId, UUID toMemberId) {
        Follow follow = Follow.createFollow(fromMemberId, toMemberId);

        if(!followRepository.existsByFromMemberAndToMember(follow)) {
            throw new CustomException(ResponseCode.NOT_FOLLOW);
        }

        followRepository.deleteByFromMemberAndToMember(follow);
    }
}
