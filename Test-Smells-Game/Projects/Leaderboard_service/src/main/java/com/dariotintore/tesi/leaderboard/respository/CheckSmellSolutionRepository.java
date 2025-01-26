package com.dariotintore.tesi.leaderboard.respository;

import com.dariotintore.tesi.leaderboard.entity.CheckSmellSolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckSmellSolutionRepository extends JpaRepository<CheckSmellSolution,Long> {
    @Query(value = "SELECT * FROM checksmell_solution S WHERE S.exercise_id = ?1 ORDER BY S.score DESC", nativeQuery = true)
    List<CheckSmellSolution> findSolutionsByExerciseId(String exerciseId);

    @Query(value = "SELECT * FROM checksmell_solution S WHERE S.player_name = ?1 AND S.exercise_id = ?2", nativeQuery = true)
    Optional<CheckSmellSolution> findSolutionByUsernameAndExerciseId(String playerName, String exerciseId);
}
