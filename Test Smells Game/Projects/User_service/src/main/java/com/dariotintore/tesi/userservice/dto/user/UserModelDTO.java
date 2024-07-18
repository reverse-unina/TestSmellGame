package com.dariotintore.tesi.userservice.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * The type User model dto.
 */
@Data
@Builder
public class UserModelDTO {
	@JsonProperty("userId")
	private Long userId;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("exp")
    private int exp;
    
}