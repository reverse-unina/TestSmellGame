package com.dariotintore.tesi.exerciseservice.entity.levelConfig;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BadgeValue {
    private String name;
    private String description;
    private int points;
    private String filename;
}
