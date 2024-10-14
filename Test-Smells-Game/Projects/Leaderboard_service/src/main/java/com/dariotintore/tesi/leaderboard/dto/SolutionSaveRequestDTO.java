package com.dariotintore.tesi.leaderboard.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolutionSaveRequestDTO {
    private String exerciseId;
    private String playerName;
    private String refactoredCode;
    private boolean refactoringResult;
    private int originalCoverage;
    private int refactoredCoverage;
    private String smells;
    private int score;
}
