package com.dariotintore.tesi.userservice.repository;


import com.dariotintore.tesi.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * The interface User repository.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Check email already exists long.
     *
     * @param email the email
     * @return the long
     */
    @Query(value = "SELECT COUNT(*) AS nelem FROM user U WHERE U.email = ?1", nativeQuery = true)
    Long checkEmailAlreadyExists(String email);

    /**
     * Gets encrypted password from email.
     *
     * @param email    the email
     * @param password the password
     * @return the encrypted password from email
     */
    @Query(value = "SELECT password FROM user  WHERE email = ?1", nativeQuery = true)
    String getEncryptedPasswordFromEmail(String email, String password);

    /**
     * Does user exist long.
     *
     * @param email the email
     * @return the long
     */
    @Query(value = "SELECT COUNT(*) as nelem FROM user U WHERE U.email = ?1", nativeQuery = true)
    Long doesUserExist(String email);

    /**
     * Gets id by email.
     *
     * @param email the email
     * @return the id by email
     */
    @Query(value = "SELECT userid FROM user U WHERE U.email =?1", nativeQuery = true)
    Long getIdByEmail(String email);

    /**
     * Does user name exist long.
     *
     * @param userName the user name
     * @return the long
     */
    @Query(value = "SELECT COUNT(*) as nelem FROM user U WHERE U.username = ?1", nativeQuery = true)
    Long doesUserNameExist(String userName);

    /**
     * Gets user by email.
     *
     * @param email the email
     * @return the user by email
     */
    @Query(value = "SELECT * FROM user U WHERE U.email = ?1", nativeQuery = true)
    Optional<User> getUserByEmail(String email);

}

