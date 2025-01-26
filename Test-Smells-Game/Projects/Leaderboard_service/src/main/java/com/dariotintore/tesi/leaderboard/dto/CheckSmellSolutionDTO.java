package com.dariotintore.tesi.leaderboard.dto;

import com.dariotintore.tesi.leaderboard.entity.CheckSmellSolution;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckSmellSolutionDTO {
    private String exerciseId;
    private String playerName;
    private int correctAnswers;
    private int wrongAnswers;
    private int missedAnswers;
    private int score;
}
