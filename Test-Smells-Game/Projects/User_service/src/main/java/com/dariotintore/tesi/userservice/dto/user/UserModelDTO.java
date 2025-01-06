package com.dariotintore.tesi.userservice.dto.user;

import com.dariotintore.tesi.userservice.entity.MissionStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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
    @JsonProperty("missionsStatus")
    private List<MissionStatus> missionsStatus;

    
}