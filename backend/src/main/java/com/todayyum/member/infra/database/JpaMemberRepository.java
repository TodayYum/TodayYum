package com.todayyum.member.infra.database;

import com.todayyum.member.dto.response.MemberListResponse;
import com.todayyum.member.infra.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface JpaMemberRepository extends JpaRepository<MemberEntity, UUID> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<MemberEntity> findByEmail(String email);

    void deleteById(UUID memberId);

    void delete(MemberEntity memberEntity);

    @Query("SELECT new com.todayyum.member.dto.response.MemberListResponse(m.id, m.nickname, m.profile, m.introduction) " +
            "FROM MemberEntity m " +
            "WHERE m.nickname LIKE %:nickname%")
    Page<MemberListResponse> findByNicknameLike(Pageable pageable, String nickname);

    @Query("SELECT new com.todayyum.member.dto.response.MemberListResponse(" +
            "f.toMember.id, f.toMember.nickname, f.toMember.profile, f.toMember.introduction)" +
            "FROM FollowEntity f WHERE f.fromMember = :fromMember")
    Page<MemberListResponse> findByFromMember(Pageable pageable, MemberEntity fromMember);

    @Query("SELECT new com.todayyum.member.dto.response.MemberListResponse(" +
            "f.fromMember.id, f.fromMember.nickname, f.fromMember.profile, f.toMember.introduction) " +
            "FROM FollowEntity f WHERE f.toMember = :toMember")
    Page<MemberListResponse> findByToMember(Pageable pageable, MemberEntity toMember);
}
