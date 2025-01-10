package com.dariotintore.tesi.exerciseservice.entity.levelconfig;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class BadgeValue {
    private String name;
    private String description;
    private int points;
    private String filename;

    public BadgeValue(
            @JsonProperty(value = "name", required = true) @NonNull String name,
            @JsonProperty(value = "description", required = true) @NonNull String description,
            @JsonProperty(value = "points", required = true) int points,
            @JsonProperty(value = "filename", required = true) @NonNull String filename) {
        this.name = name;
        this.description = description;
        this.points = points;
        this.filename = filename;
    }
}
