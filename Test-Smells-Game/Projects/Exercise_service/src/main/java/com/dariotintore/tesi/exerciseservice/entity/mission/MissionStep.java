package com.dariotintore.tesi.exerciseservice.entity.mission;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
public class MissionStep {
    private String id;
    private String type;

    @JsonCreator
    public MissionStep(
            @JsonProperty(value = "id", required = true) @NonNull String id,
            @JsonProperty(value = "type", required = true) @NonNull String type
    ) {
        this.id = id;
        this.type = type;
    }
}
