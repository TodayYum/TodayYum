package com.todayyum.auth.util;

import com.todayyum.auth.application.repository.TokenRepository;
import com.todayyum.auth.domain.Token;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class JWTUtil {

    private SecretKey accessSecretKey;
    private SecretKey refreshSecretKey;
    private final Long accessTokenValidTime = Duration.ofMinutes(30).toMillis();
    private final Long refreshTokenValidTime = Duration.ofDays(14).toMillis();
    private final TokenRepository tokenRepository;


    public JWTUtil(@Value("${spring.jwt.accessSecret}")String accessSecret, @Value("${spring.jwt.refreshSecret}")String refreshSecret, @Autowired TokenRepository tokenRepository) {
        this.accessSecretKey = new SecretKeySpec(accessSecret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.refreshSecretKey = new SecretKeySpec(refreshSecret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.tokenRepository = tokenRepository;
    }

    public UUID getMemberId(String token) {
        return UUID.fromString(Jwts.parser().verifyWith(accessSecretKey).build().parseSignedClaims(token).getPayload().get("memberId", String.class));
    }

    public String getRole(String token, String type) {
        return Jwts.parser().verifyWith(type.equals("access") ? accessSecretKey : refreshSecretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public boolean isExpired(String token) {
        return Jwts.parser().verifyWith(accessSecretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String createAccessToken(UUID memberId, String role) {
        return Jwts.builder()
                .claim("memberId", memberId)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenValidTime))
                .signWith(accessSecretKey)
                .compact();
    }

    public String createRefreshToken(UUID memberId, String role) {
        String refreshToken = Jwts.builder()
                .claim("memberId", memberId)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshTokenValidTime))
                .signWith(refreshSecretKey)
                .compact();
        tokenRepository.save(new Token(refreshToken, memberId));

        return refreshToken;
    }

}
