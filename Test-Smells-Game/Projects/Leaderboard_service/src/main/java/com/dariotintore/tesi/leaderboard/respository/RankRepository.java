package com.dariotintore.tesi.leaderboard.respository;

import com.dariotintore.tesi.leaderboard.entity.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RankRepository extends JpaRepository<Rank, Long> {
    @Query("SELECT r FROM Rank r WHERE r.userId = :userId")
    Optional<Rank> findByUserId(Long userId);

}