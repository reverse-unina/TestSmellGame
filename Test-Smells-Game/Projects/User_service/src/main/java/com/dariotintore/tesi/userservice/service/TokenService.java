package com.dariotintore.tesi.userservice.service;

import com.dariotintore.tesi.userservice.entity.Token;
import com.dariotintore.tesi.userservice.repository.TokenRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Random;

/**
 * The type Token service.
 */
@Transactional
@Service
public class TokenService {
    
    @Autowired
    private TokenRepository tokenRepository;

    private Random rand = SecureRandom.getInstanceStrong();

    /**
     * Instantiates a new Token service.
     *
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public TokenService() throws NoSuchAlgorithmException {
        // Empty
    }

    /**
     * Generate token String.
     *
     * @param email the email
     * @return the string
     */
    public String generateToken(String email) {
        Instant now = Instant.now();
        String jwt = Jwts.builder()
                .setSubject(email)
                .setAudience("token-generation")
                .claim("1d20", this.rand.nextInt(20) + 1).setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(1, ChronoUnit.MINUTES)))
                .compact();
        Token t = Token.builder()
                .email(email)
                .token(jwt)
                .build();
        saveToken(t);
        return jwt;
    }

    /**
     * Save token.
     *
     * @param tok the tok
     */
    public void saveToken(Token tok) {
        if (checkTokenAlreadyExists(tok.getEmail())) deleteToken(tok.getEmail());
        tokenRepository.save(tok);
    }

    /**
     * Check token already exists boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public boolean checkTokenAlreadyExists(String email) {
        return tokenRepository.checkTokenAlreadyExists(email) >= 1;
    }

    /**
     * Delete token.
     *
     * @param email the email
     */
    public void deleteToken(String email) {
        tokenRepository.deleteByEmail(email);
    }

    /**
     * Validate token boolean.
     *
     * @param email the email
     * @param token the token
     * @return the boolean
     */
    public boolean validateToken(String email, String token) {
        return tokenRepository.validateToken(email, token) > 0;
    }

}
