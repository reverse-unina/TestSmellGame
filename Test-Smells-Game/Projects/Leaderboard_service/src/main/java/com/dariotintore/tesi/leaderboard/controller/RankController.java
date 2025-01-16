package com.dariotintore.tesi.leaderboard.controller;

import com.dariotintore.tesi.leaderboard.dto.PodiumDTO;
import com.dariotintore.tesi.leaderboard.entity.Score;
import com.dariotintore.tesi.leaderboard.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/rank")
public class RankController {

    @Autowired
    private ScoreService scoreService;

    @PostMapping("/{username}")
    public ResponseEntity<Score> createNewUserScore(@PathVariable(name = "username") String userName) {
        this.scoreService.createNewRank(userName);
        Optional<Score> score = this.scoreService.getScore(userName);
        return score.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.internalServerError().build());
    }

    @GetMapping("/{username}/ranking")
    public ResponseEntity<Map<String, Object>> getUserRanking(@PathVariable(name = "username") String userName) {
        Map<String, Object> ranking = scoreService.getUserRank(userName);

        if (!ranking.isEmpty())
            return ResponseEntity.ok(ranking);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<Score> getUserScore(@PathVariable(name = "username") String userName) {
        Optional<Score> score = scoreService.getScore(userName);

        if (score.isPresent()) {
            return ResponseEntity.ok(score.get());
        } else {
            Score emptyScore = new Score();
            emptyScore.setUserName(userName);
            return ResponseEntity.ok(emptyScore);
        }
    }

    @GetMapping("/podium/gamemode")
    public ResponseEntity<Map<String, List<PodiumDTO>>> getPodiumGameMode(@RequestParam int podiumDimension) {
        Map<String, List<PodiumDTO>> podiumDTOMap = scoreService.getGameModePodium(podiumDimension);
        return ResponseEntity.ok(podiumDTOMap);
    }

    @GetMapping("/podium/refactoring")
    public ResponseEntity<Map<String, List<PodiumDTO>>> getPodiumRefactoringExercises(@RequestParam int podiumDimension) {
        Map<String, List<PodiumDTO>> podiumDTOMap = scoreService.getRefactoringExercisePodium(podiumDimension);
        return ResponseEntity.ok(podiumDTOMap);
    }

    @PutMapping("/{username}/missions")
    public ResponseEntity<Score> updateUserScore(
            @PathVariable(name = "username") String userName,
            @RequestParam int score) {
        Score updatedScore = scoreService.updateMissionsScore(userName, score);
        return ResponseEntity.ok(updatedScore);
    }

    @PutMapping("/{username}/refactoring")
    public ResponseEntity<Score> updateBestRefactoringScore(
            @PathVariable(name = "username") String userName,
            @RequestParam String exerciseId,
            @RequestParam int score) {
        Score updatedScore = scoreService.updateBestRefactoringScore(userName, exerciseId, score);
        return ResponseEntity.ok(updatedScore);
    }

    @PutMapping("/{username}/checksmell")
    public ResponseEntity<Score> updateBestCheckSmellScore(
            @PathVariable(name = "username") String userName,
            @RequestParam String exerciseId,
            @RequestParam int score) {
        Score updatedScore = scoreService.updateBestCheckSmellGameScore(userName, exerciseId, score);
        return ResponseEntity.ok(updatedScore);
    }
}
