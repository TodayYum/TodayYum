package com.todayyum.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Setter
@Getter
@Builder
public class ProfileModifyRequest {

    private UUID memberId;
    private MultipartFile profile;
}
