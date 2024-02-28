package com.todayyum.auth.infra.database;

import com.todayyum.auth.infra.entity.TokenEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RedisTokenRepository extends CrudRepository<TokenEntity, UUID> {
}
