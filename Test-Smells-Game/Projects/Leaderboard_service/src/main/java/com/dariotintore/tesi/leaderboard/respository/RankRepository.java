package com.dariotintore.tesi.leaderboard.respository;

import com.dariotintore.tesi.leaderboard.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RankRepository extends JpaRepository<Score, Long> {
    Optional<Score> findByUserName(String userName);
}