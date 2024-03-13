package com.todayyum.member.application;

import com.todayyum.member.application.repository.FollowRepository;
import com.todayyum.member.dto.response.FollowListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindFollowUseCase {
    private final FollowRepository followRepository;

    @Transactional
    public List<FollowListResponse> listFollowing(UUID fromMemberId) {
        return followRepository.findByFromMember(fromMemberId);
    }

    @Transactional
    public List<FollowListResponse> listFollower(UUID toMemberId) {
        return followRepository.findByToMember(toMemberId);
    }

}
