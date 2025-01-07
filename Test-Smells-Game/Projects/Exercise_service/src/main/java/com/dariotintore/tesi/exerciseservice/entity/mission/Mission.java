package com.dariotintore.tesi.exerciseservice.entity.mission;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import java.util.List;

public class Mission {
    private String id;
    private String name;
    private String badge;
    private String badgeFilename;
    private List<MissionStep> steps;

    @JsonCreator
    public Mission(
            @JsonProperty(value = "id", required = true) @NonNull String id,
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getBadgeFilename() {
        return badgeFilename;
    }

    public void setBadgeFilename(String badgeFilename) {
        this.badgeFilename = badgeFilename;
    }

    public List<MissionStep> getSteps() {
        return steps;
    }

    public void setSteps(List<MissionStep> steps) {
        this.steps = steps;
    }
}
