package com.todayyum.member.application;

import com.todayyum.board.application.repository.BoardImageRepository;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import com.todayyum.global.util.S3Util;
import com.todayyum.member.application.repository.MemberRepository;
import com.todayyum.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RemoveMemberUseCase {

    private final MemberRepository memberRepository;
    private final BoardImageRepository boardImageRepository;
    private final S3Util s3Util;

    @Transactional
    public void removeMember(UUID memberId) {
        if(memberId == null) {
            throw new CustomException(ResponseCode.INTERNAL_SERVER_ERROR);
        }

        boardImageRepository.findByMemberId(memberId).stream()
                        .forEach(boardImage -> s3Util.removeFile(boardImage.getLink()));

        Member member = memberRepository.findById(memberId);

        s3Util.removeFile(member.getProfile());

        memberRepository.deleteById(memberId);
    }
}
