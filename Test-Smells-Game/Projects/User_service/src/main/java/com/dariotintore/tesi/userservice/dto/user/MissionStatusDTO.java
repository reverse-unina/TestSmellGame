package com.dariotintore.tesi.userservice.dto.user;

import com.dariotintore.tesi.userservice.entity.MissionStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionStatusDTO {
    @JsonProperty("email")
    private String email;

    @JsonProperty("missionStatus")
    private MissionStatus missionStatus;
}
