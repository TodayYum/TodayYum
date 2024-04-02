package com.todayyum.member.application.repository;

import com.todayyum.member.domain.Follow;
import com.todayyum.member.dto.response.FollowListResponse;

import java.util.List;
import java.util.UUID;

public interface FollowRepository {

    Follow save(Follow follow);

    boolean existsByFromMemberAndToMember(Follow follow);

    Long countByFromMember(UUID fromMemberId);

    Long countByToMember(UUID toMemberId);

    void delete(Follow follow);

    List<FollowListResponse> findByFromMember(UUID fromMemberId);

    List<FollowListResponse> findByToMember(UUID toMemberId);

}
