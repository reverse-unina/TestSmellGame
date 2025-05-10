package com.dariotintore.tesi.leaderboard.respository;

import com.dariotintore.tesi.leaderboard.entity.RefactoringSolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefactoringSolutionRepository extends JpaRepository<RefactoringSolution,Long> {
  @Query(value = "SELECT * FROM refactoring_solution S WHERE S.exercise_id = ?1 ORDER BY S.score DESC", nativeQuery = true)
  List<RefactoringSolution> findSolutionsByExerciseId(String exerciseId);

}
