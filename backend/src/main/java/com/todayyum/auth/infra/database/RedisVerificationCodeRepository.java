package com.todayyum.auth.infra.database;

import com.todayyum.auth.infra.entity.VerificationCodeEntity;
import org.springframework.data.repository.CrudRepository;

public interface RedisVerificationCodeRepository extends CrudRepository<VerificationCodeEntity, String> {
}
