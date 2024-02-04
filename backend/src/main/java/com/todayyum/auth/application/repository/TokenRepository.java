package com.todayyum.auth.application.repository;

import com.todayyum.auth.domain.Token;

public interface TokenRepository {

    public void save(Token token);

    public Token findByRefreshToken(String refreshToken);

    public void deleteByRefreshToken(String refreshToken);

}
