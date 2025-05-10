package com.dariotintore.tesi.leaderboard.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "checksmell_solution")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckSmellSolution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solution_id", unique = true, updatable = false)
    private Long solutionId;

    @Column(name = "exercise_id")
    private String exerciseId;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "score", columnDefinition = "int default 0")
    private int score;

    @Column(name = "correct_answers", columnDefinition = "int default 0")
    private int correctAnswers;

    @Column(name = "wrong_answers", columnDefinition = "int default 0")
    private int wrongAnswers;

    @Column(name = "missed_answers", columnDefinition = "int default 0")
    private int missedAnswers;
}
