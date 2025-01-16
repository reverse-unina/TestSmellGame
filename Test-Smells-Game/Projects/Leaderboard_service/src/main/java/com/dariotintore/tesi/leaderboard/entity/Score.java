package com.dariotintore.tesi.leaderboard.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
@Table(name = "score")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "refactoring_score", nullable = false)
    private Integer refactoringScore = 0;

    @Column(name = "check_smell_score", nullable = false)
    private Integer checkSmellScore = 0;

    @Column(name = "missions_score", nullable = false)
    private Integer missionsScore = 0;

    @ElementCollection
    @CollectionTable(name = "check_smell_scores", joinColumns = @JoinColumn(name = "score_id"))
    @MapKeyColumn(name = "exercise_id")
    @Column(name = "best_score_check_smell")
    private Map<String, Integer> bestCheckSmellScore = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "refactoring_scores", joinColumns = @JoinColumn(name = "score_id"))
    @MapKeyColumn(name = "exercise_id")
    @Column(name = "best_score_refactoring")
    private Map<String, Integer> bestRefactoringScores = new HashMap<>();
}
