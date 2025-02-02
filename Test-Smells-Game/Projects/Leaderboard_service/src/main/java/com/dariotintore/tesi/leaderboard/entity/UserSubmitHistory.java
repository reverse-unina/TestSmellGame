package com.dariotintore.tesi.leaderboard.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "user_submit_history")
public class UserSubmitHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "exercise_score")
    private Integer exerciseScore;

    @Column(name = "exercise_type")
    private String exerciseType;

    @Column(name = "exercise_name")
    private String exerciseName;

    @Column(name = "date_time")
    private LocalDateTime dateTime = LocalDateTime.now();
}
