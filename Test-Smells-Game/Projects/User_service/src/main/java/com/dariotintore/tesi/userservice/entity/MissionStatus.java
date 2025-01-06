package com.dariotintore.tesi.userservice.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MissionStatus {
    private String missionId;
    private Integer steps;

}
