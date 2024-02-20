package com.todayyum.auth.application.repository;

import com.todayyum.auth.domain.VerificationCode;

public interface VerificationCodeRepository {
    public void save(VerificationCode verificationCode);
    public void deleteByEmail(String email);
    public VerificationCode findByEmail(String email);
}
