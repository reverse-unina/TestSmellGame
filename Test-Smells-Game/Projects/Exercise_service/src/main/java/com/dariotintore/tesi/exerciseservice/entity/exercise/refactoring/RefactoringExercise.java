package com.dariotintore.tesi.exerciseservice.entity.exercise.refactoring;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RefactoringExercise {
    private String exerciseId;
    private String className;
    private RefactoringGameConfiguration refactoringGameConfiguration;
    private boolean autoValutative;

    @JsonCreator
    public RefactoringExercise(
            @JsonProperty(value = "exerciseId", required = true) @NonNull String exerciseId,
            @JsonProperty(value = "class_name", required = true) @NonNull String className,
            @JsonProperty(value = "refactoring_game_configuration", required = true) @NonNull RefactoringGameConfiguration refactoringGameConfiguration,
            @JsonProperty(value = "auto_valutative", required = true) boolean autoValutative
    ) {
        this.exerciseId = exerciseId;
        this.className = className;
        this.refactoringGameConfiguration = refactoringGameConfiguration;
        this.autoValutative = autoValutative;
    }

    @Getter
    @Setter
    public static class RefactoringGameConfiguration {
        List<String> dependencies;
        int refactoringLimit;
        int smellsAllowed;
        int level;
        List<TestSmellType> ignoredSmells;

        @JsonCreator
        public RefactoringGameConfiguration(
                @JsonProperty(value = "dependencies", required = true) @NonNull List<String> dependencies,
                @JsonProperty(value = "refactoring_limit", required = true) int refactoringLimit,
                @JsonProperty(value = "smells_allowed", required = true) int smellsAllowed,
                @JsonProperty(value = "level", required = true) int level,
                @JsonProperty(value = "ignored_smells", required = true) @NonNull List<TestSmellType> ignoredSmells
        ) {
            this.dependencies = dependencies;
            this.refactoringLimit = refactoringLimit;
            this.smellsAllowed = smellsAllowed;
            this.level = level;
            this.ignoredSmells = ignoredSmells;
        }
    }
}
