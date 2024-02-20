package com.todayyum.auth.infra.database;

import com.todayyum.auth.infra.entity.TokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface RedisTokenRepository extends CrudRepository<TokenEntity, String> {
}
