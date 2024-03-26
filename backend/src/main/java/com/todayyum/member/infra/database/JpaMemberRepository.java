package com.todayyum.member.infra.database;

import com.todayyum.member.infra.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaMemberRepository extends JpaRepository<MemberEntity, UUID> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<MemberEntity> findByEmail(String email);

//    @Modifying(clearAutomatically = true)
//    @Query("UPDATE MemberEntity m SET m.isDeleted = TRUE WHERE m.id = :memberId")
    void deleteById(UUID memberId);

//    @Modifying(clearAutomatically = true)
//    @Query("UPDATE MemberEntity m SET m.isDeleted = TRUE WHERE m = :memberEntity")
    void delete(MemberEntity memberEntity);
}
