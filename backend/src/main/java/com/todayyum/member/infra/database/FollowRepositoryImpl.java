package com.todayyum.member.infra.database;

import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import com.todayyum.member.application.repository.FollowRepository;
import com.todayyum.member.domain.Follow;
import com.todayyum.member.dto.response.FollowListResponse;
import com.todayyum.member.infra.entity.FollowEntity;
import com.todayyum.member.infra.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class FollowRepositoryImpl implements FollowRepository {

    private final JpaFollowRepository jpaFollowRepository;
    private final JpaMemberRepository jpaMemberRepository;

    @Override
    public Follow save(Follow follow) {
        MemberEntity fromMember = jpaMemberRepository.findById(follow.getFromMemberId())
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        MemberEntity toMember = jpaMemberRepository.findById(follow.getToMemberId())
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        FollowEntity followEntity = FollowEntity.builder()
                .fromMember(fromMember)
                .toMember(toMember)
                .build();

        return Follow.createFollow(jpaFollowRepository.save(followEntity));
    }

    @Override
    public boolean existsByFromMemberAndToMember(Follow follow) {
        MemberEntity fromMember = jpaMemberRepository.findById(follow.getFromMemberId())
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        MemberEntity toMember = jpaMemberRepository.findById(follow.getToMemberId())
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        return jpaFollowRepository.existsByFromMemberAndToMember(fromMember, toMember);
    }

    @Override
    public Long countByFromMember(UUID fromMemberId) {
        MemberEntity fromMember = jpaMemberRepository.findById(fromMemberId)
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        return jpaFollowRepository.countByFromMember(fromMember);
    }

    @Override
    public Long countByToMember(UUID toMemberId) {
        MemberEntity toMember = jpaMemberRepository.findById(toMemberId)
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        return jpaFollowRepository.countByToMember(toMember);
    }

    @Override
    public void deleteByFromMemberAndToMember(Follow follow) {
        MemberEntity fromMember = jpaMemberRepository.findById(follow.getFromMemberId())
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        MemberEntity toMember = jpaMemberRepository.findById(follow.getToMemberId())
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        if(!jpaFollowRepository.existsByFromMemberAndToMember(fromMember, toMember)) {
            throw new CustomException(ResponseCode.NOT_FOLLOWING);
        }

        jpaFollowRepository.deleteByFromMemberAndToMember(fromMember, toMember);
    }

    @Override
    public List<FollowListResponse> findByFromMember(UUID fromMemberId) {
        MemberEntity fromMember = jpaMemberRepository.findById(fromMemberId)
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        return jpaFollowRepository.findByFromMember(fromMember);
    }

    @Override
    public List<FollowListResponse> findByToMember(UUID toMemberId) {
        MemberEntity toMember = jpaMemberRepository.findById(toMemberId)
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_ID_NOT_FOUND));

        return jpaFollowRepository.findByToMember(toMember);
    }

}
