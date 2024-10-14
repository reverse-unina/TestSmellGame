package com.dariotintore.tesi.userservice.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO definito per effettuare il login all'interno del sistema.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserDTO {
    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("username")
    private String username;
    
    @JsonProperty("exp")
    private int exp;
}
