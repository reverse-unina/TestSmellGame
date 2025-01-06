package com.dariotintore.tesi.leaderboard.controller;

import com.dariotintore.tesi.leaderboard.entity.Rank;
import com.dariotintore.tesi.leaderboard.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/rank")
public class RankController {

    @Autowired
    private RankService rankService;

    @GetMapping("/{userId}/ranking")
    public ResponseEntity<Map<String, Object>> getUserRanking(@PathVariable Long userId) {
        Map<String, Object> ranking = rankService.getUserRank(userId);
        return ResponseEntity.ok(ranking);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Rank> getUserScore(@PathVariable Long userId) {
        Rank rank = rankService.getScore(userId).isPresent() ? rankService.getScore(userId).get() : null;
        return ResponseEntity.ok(rank);
    }

    @PostMapping("/{userId}/score")
    public ResponseEntity<Rank> updateUserScore(
            @PathVariable Long userId,
            @RequestParam String gameMode,
            @RequestParam int score) {
        Rank rank = rankService.updateScore(userId, gameMode, score);
        return ResponseEntity.ok(rank);
    }

    @PostMapping("/{userId}/refactoring")
    public ResponseEntity<Rank> updateBestRefactoringScore(
            @PathVariable Long userId,
            @RequestParam String exerciseId,
            @RequestParam int score) {
        Rank rank = rankService.updateBestRefactoringScore(userId, exerciseId, score);
        return ResponseEntity.ok(rank);
    }
}
