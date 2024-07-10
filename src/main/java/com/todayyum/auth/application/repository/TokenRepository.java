package com.todayyum.auth.application.repository;

import com.todayyum.auth.domain.Token;

import java.util.UUID;

public interface TokenRepository {

    public void save(Token token);

    public Token findByMemberId(UUID memberId);

    public void deleteByMemberId(UUID memberId);

}
