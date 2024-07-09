package com.todayyum.member.infra.database;

import com.todayyum.member.infra.entity.FollowEntity;
import com.todayyum.member.infra.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface JpaFollowRepository extends JpaRepository<FollowEntity, Long> {
    boolean existsByFromMemberAndToMember(MemberEntity fromMember, MemberEntity toMember);

    Long countByFromMember(MemberEntity fromMember);

    Long countByToMember(MemberEntity toMember);

    void deleteByFromMemberAndToMember(MemberEntity fromMember, MemberEntity toMember);

    @Query("SELECT f " +
            "FROM FollowEntity f " +
            "WHERE f.fromMember.id = :fromMemberId " +
            "AND f.toMember.id IN :toMemberIds")
    List<FollowEntity> findByFromMemberAndToMembers(UUID fromMemberId, List<UUID> toMemberIds);
}
