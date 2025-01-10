package com.dariotintore.tesi.exerciseservice.entity.mission;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.List;

@Getter
@Setter
public class Mission {
    private String id;
    private String name;
    private String badge;
    private String badgeFilename;
    private List<MissionStep> steps;

    @JsonCreator
    public Mission(
            @JsonProperty(value = "missionId", required = true) @NonNull String id,
            @JsonProperty(value = "name", required = true) @NonNull String name,
            @JsonProperty(value = "badge", required = true) @NonNull String badge,
            @JsonProperty(value = "badge_filename", required = true) @NonNull String badgeFilename,
            @JsonProperty(value = "steps", required = true) @NonNull List<MissionStep> steps) {
        this.id = id;
        this.name = name;
        this.badgeFilename = badgeFilename;
        this.badge = badge;
        this.steps = steps;
    }

    @Getter
    @Setter
    public static class MissionStep {
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

}
