package com.todayyum.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@Builder
public class ProfileModifyRequest {

    private Long memberId;
    private MultipartFile profile;
}
