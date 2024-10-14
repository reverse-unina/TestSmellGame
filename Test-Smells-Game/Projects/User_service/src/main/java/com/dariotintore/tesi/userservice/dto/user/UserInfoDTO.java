package com.dariotintore.tesi.userservice.dto.user;

import lombok.Builder;
import lombok.Data;

/**
 * DTO definito per ottenere le informazioni principali dell'utente.
 */
@Data
@Builder
public class UserInfoDTO {
    private Long userId;
    private String email;
    private String userName;
    private String firstName;
    private String lastName;
    private String token;
    private int exp;  
}
