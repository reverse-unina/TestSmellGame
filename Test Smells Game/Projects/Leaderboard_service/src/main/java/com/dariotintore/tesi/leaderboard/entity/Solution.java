package com.dariotintore.tesi.leaderboard.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "solution")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Solution {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "solution_id", unique = true, updatable = false)
  private Long solutionId;

  @Column(name = "exercise_id")
  private String exerciseId;

  @Column(name = "player_name")
  private String playerName;

  @Column(name = "refactored_code", columnDefinition = "TEXT")
  private String refactoredCode;

  @Column(name = "creation_timestamp")
  @CreationTimestamp
  private Date creationTimestamp;

  @OneToMany
  @JoinColumn(name="solution_id")
  private List<Comment> commentList;

  @Column(name = "positive_vote", columnDefinition = "int default 0")
  private int upVotes;

  @Column(name = "negative_vote", columnDefinition = "int default 0")
  private int downVotes;

  @Column(name = "score", columnDefinition = "int default 0")
  private int score;

  @Column(name = "refactoring_result", columnDefinition = "TEXT")
  private boolean refactoringResult;

  @Column(name = "original_coverage", columnDefinition = "int default 0")
  private int originalCoverage;

  @Column(name = "refactored_coverage", columnDefinition = "int default 0")
  private int refactoredCoverage;

  @Column(name = "smells", columnDefinition = "TEXT")
  private String smells;
}