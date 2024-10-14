package com.dariotintore.tesi.leaderboard.respository;

import com.dariotintore.tesi.leaderboard.entity.Solution;
import com.dariotintore.tesi.leaderboard.entity.UserSolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<UserSolution,Long> {

    @Query(value = "SELECT COUNT(*) AS nelem FROM USER_SOLUTION U WHERE U.SOLUTION_ID = ?1 AND VOTE = 0", nativeQuery = true)
    public Long getUpvotesNumber(Long solutionId);

    @Query(value = "SELECT COUNT(*) AS nelem FROM USER_SOLUTION U WHERE U.SOLUTION_ID = ?1 AND VOTE = 1", nativeQuery = true)
    public Long getDownvotesNumber(Long solutionId);

    @Query(value = "SELECT * FROM USER_SOLUTION U WHERE U.SOLUTION_ID = ?1 AND USER_ID = ?2", nativeQuery = true)
    public Optional<UserSolution> findByUserAndSolutionId(Long solutionId, Long userId);

}
