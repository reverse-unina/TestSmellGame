package com.dariotintore.tesi.userservice.dto.user;

import java.util.Date;

public class TestResultDTO {
    private Integer refactoringScore;
    private Integer checkScore;
    private Integer totalScore;
    private int correctAnswers;
    private int wrongAnswers;
    private String completionTime;
    private Date date;


    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public String getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(String completionTime) {
        this.completionTime = completionTime;
    }

    public Integer getRefactoringScore() {
        return refactoringScore;
    }

    public void setRefactoringScore(Integer refactoringScore) {
        this.refactoringScore = refactoringScore;
    }

    public Integer getCheckScore() {
        return checkScore;
    }

    public void setCheckScore(Integer checkScore) {
        this.checkScore = checkScore;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

