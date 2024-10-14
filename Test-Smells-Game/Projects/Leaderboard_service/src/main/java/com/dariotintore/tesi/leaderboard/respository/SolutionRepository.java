package com.dariotintore.tesi.leaderboard.respository;

import com.dariotintore.tesi.leaderboard.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SolutionRepository extends JpaRepository<Solution,Long> {
  @Query(value = "SELECT * FROM solution S WHERE S.exercise_id = ?1 ORDER BY S.score ASC", nativeQuery = true)
  List<Solution> findSolutionsByExerciseId(String exerciseId);

}
