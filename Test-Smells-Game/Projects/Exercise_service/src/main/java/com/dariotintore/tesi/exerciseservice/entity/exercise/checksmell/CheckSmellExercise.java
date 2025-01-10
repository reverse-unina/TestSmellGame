package com.dariotintore.tesi.exerciseservice.entity.exercise.checksmell;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.List;

@Getter
@Setter
public class CheckSmellExercise {
    private String exerciseId;
    private CheckGameConfiguration checkGameConfiguration;
    private boolean autoValutative;

    @JsonCreator
    public CheckSmellExercise(
            @JsonProperty(value = "exerciseId", required = true) @NonNull String exerciseId,
            @JsonProperty(value = "check_game_configuration", required = true) CheckGameConfiguration checkGameConfiguration,
            @JsonProperty(value = "auto_valutative", required = true) boolean autoValutative) {
        this.exerciseId = exerciseId;
        this.checkGameConfiguration = checkGameConfiguration;
        this.autoValutative = autoValutative;
    }

    @Getter
    @Setter
    public static class CheckGameConfiguration {
        private List<Question> questions;
        private int level;

        @JsonCreator
        public CheckGameConfiguration(@JsonProperty(value = "questions", required = true) @NonNull List<Question> questions,
                                      @JsonProperty(value = "level", required = true) int level) {
            this.questions = questions;
            this.level = level;
        }

        @Getter
        @Setter
        public static class Question {
            private String questionTitle;
            private String questionCode;
            private List<Answer> answers;

            @JsonCreator
            public Question(@JsonProperty(value = "questionTitle", required = true) @NonNull String questionTitle, @JsonProperty(value = "questionCode", required = true) String questionCode, @JsonProperty(value = "answers", required = true) List<Answer> answers) {
                this.questionTitle = questionTitle;
                this.questionCode = questionCode;
                this.answers = answers;
            }

            @Getter
            @Setter
            public static class Answer {
                private String answerText;
                private boolean isCorrect;

                @JsonCreator
                public Answer(@JsonProperty(value = "answerText", required = true) @NonNull String answerText, @JsonProperty(value = "isCorrect", required = true) boolean isCorrect) {
                    this.answerText = answerText;
                    this.isCorrect = isCorrect;
                }
            }
        }
    }
}
