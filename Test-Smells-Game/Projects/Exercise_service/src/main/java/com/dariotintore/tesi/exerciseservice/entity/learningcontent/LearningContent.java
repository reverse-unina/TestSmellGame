package com.dariotintore.tesi.exerciseservice.entity.learningcontent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class LearningContent {
    String learningId;
    String title;
    String content;
    String externalReference;

    @JsonCreator
    public LearningContent(
            @JsonProperty(value = "learningId", required = true) @NonNull String learningId,
            @JsonProperty(value = "title", required = true) @NonNull String title,
            @JsonProperty(value = "content", required = true) @NonNull String content,
            @JsonProperty(value = "external_reference", required = true) String externalReference) {
        this.learningId = learningId;
        this.title = title;
        this.content = content;
        this.externalReference = externalReference;
    }
}
