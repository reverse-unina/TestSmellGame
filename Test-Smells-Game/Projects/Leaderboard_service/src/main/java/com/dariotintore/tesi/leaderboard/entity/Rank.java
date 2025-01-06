package com.dariotintore.tesi.leaderboard.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
@Table(name = "rank")
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "refactoring_score", nullable = false)
    private Integer refactoringScore = 0;

    @Column(name = "check_smell_score", nullable = false)
    private Integer checkSmellScore = 0;

    @Column(name = "missions_score", nullable = false)
    private Integer missionsScore = 0;

    @ElementCollection
    @CollectionTable(name = "refactoring_scores", joinColumns = @JoinColumn(name = "rank_id"))
    @MapKeyColumn(name = "exercise_id")
    @Column(name = "best_score")
    private Map<String, Integer> bestRefactoringScores = new HashMap<>();
}
