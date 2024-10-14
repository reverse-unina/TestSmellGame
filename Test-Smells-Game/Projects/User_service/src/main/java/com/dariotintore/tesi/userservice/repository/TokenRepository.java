package com.dariotintore.tesi.userservice.repository;

import com.dariotintore.tesi.userservice.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * The interface Token repository.
 */
public interface TokenRepository extends JpaRepository<Token, Long> {
    /**
     * Check token already exists long.
     * This method helps us know if the user already have a token
     *
     * @param email the email
     * @return the long
     */
    @Query(value = "SELECT COUNT(*) AS nelem FROM activetokens A WHERE A.email = ?1", nativeQuery = true)
    public Long checkTokenAlreadyExists(String email);

    /**
     * Validate token long.
     * This method helps us know if
     * @param email the email
     * @param token the token
     * @return the long
     */
    @Query(value = "SELECT COUNT(*) AS nelem FROM activetokens WHERE email = ?1 AND token = ?2", nativeQuery = true)
    public Long validateToken(String email, String token);

    /**
     * Delete by email.
     *
     * @param email the email
     */
    @Modifying
    @Query(value = "delete from activetokens where email = ?1", nativeQuery = true)
    public void deleteByEmail(String email);

}
