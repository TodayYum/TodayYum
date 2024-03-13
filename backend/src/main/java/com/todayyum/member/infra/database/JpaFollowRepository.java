package com.todayyum.member.infra.database;

import com.todayyum.member.dto.response.FollowListResponse;
import com.todayyum.member.infra.entity.FollowEntity;
import com.todayyum.member.infra.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaFollowRepository extends JpaRepository<FollowEntity, Long> {
    boolean existsByFromMemberAndToMember(MemberEntity fromMember, MemberEntity toMember);

    Long countByFromMember(MemberEntity fromMember);

    Long countByToMember(MemberEntity toMember);

    void deleteByFromMemberAndToMember(MemberEntity fromMember, MemberEntity toMember);

    @Query("SELECT new com.todayyum.member.dto.response.FollowListResponse(" +
            "f.toMember.id, f.toMember.nickname, f.toMember.profile)" +
            "FROM FollowEntity f WHERE f.fromMember = :fromMember")
    List<FollowListResponse> findByFromMember(MemberEntity fromMember);

    @Query("SELECT new com.todayyum.member.dto.response.FollowListResponse(" +
            "f.fromMember.id, f.fromMember.nickname, f.fromMember.profile) " +
            "FROM FollowEntity f WHERE f.toMember = :toMember")
    List<FollowListResponse> findByToMember(MemberEntity toMember);
}
