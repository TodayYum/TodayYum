package com.todayyum.auth.infra.database;

import com.todayyum.auth.application.repository.VerificationCodeRepository;
import com.todayyum.auth.domain.VerificationCode;
import com.todayyum.auth.infra.entity.VerificationCodeEntity;
import com.todayyum.global.dto.response.ResponseCode;
import com.todayyum.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@Slf4j
public class VerificationCodeRepositoryImpl implements VerificationCodeRepository {

    private final RedisVerificationCodeRepository redisVerificationCodeRepository;

    @Override
    public VerificationCode save(VerificationCode verificationCode) {
        VerificationCodeEntity verificationCodeEntity = verificationCode.createEntity();
        verificationCodeEntity = redisVerificationCodeRepository.save(verificationCodeEntity);

        return VerificationCode.createVerificationCode(verificationCodeEntity);
    }

    @Override
    public void deleteByEmail(String email) {
        redisVerificationCodeRepository.deleteById(email);
    }

    @Override
    public VerificationCode findByEmail(String email) {
        VerificationCodeEntity verificationCodeEntity = redisVerificationCodeRepository.findById(email)
                .orElseThrow(() -> new CustomException(ResponseCode.EMAIL_VERIFICATION_FAILED));

        return VerificationCode.createVerificationCode(verificationCodeEntity);
    }
}
