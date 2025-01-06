package com.dariotintore.tesi.leaderboard.service;

import com.dariotintore.tesi.leaderboard.entity.Rank;
import com.dariotintore.tesi.leaderboard.respository.RankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RankService {
    @Autowired
    private RankRepository rankRepository;

    public Optional<Rank> getScore(Long userId) {
        return rankRepository.findByUserId(userId);
    }

    public Rank updateScore(Long userId, String gameMode, int score) {
        Rank rank = rankRepository.findByUserId(userId)
                .orElseGet(() -> createNewRank(userId));

        switch (gameMode.toLowerCase()) {
            case "refactoring":
                rank.setRefactoringScore(rank.getRefactoringScore() + score);
                break;
            case "check-smell":
                rank.setCheckSmellScore(rank.getCheckSmellScore() + score);
                break;
            case "mission":
                rank.setMissionsScore(rank.getMissionsScore() + score);
                break;
            default:
                throw new IllegalArgumentException("Invalid game mode: " + gameMode);
        }

        return rankRepository.save(rank);
    }

    public Rank updateBestRefactoringScore(Long userId, String exerciseId, int score) {
        Rank rank = rankRepository.findByUserId(userId).get();

        rank.getBestRefactoringScores().merge(exerciseId, score, Math::max);
        return rankRepository.save(rank);
    }

    private Rank createNewRank(Long userId) {
        System.out.println("Creating new rank");
        Rank rank = new Rank();
        rank.setUserId(userId);
        System.out.println("New rank id: " + rank.getUserId());
        return rankRepository.save(rank);
    }

    public Map<String, Object> getUserRank(Long userId) {
        Map<String, Object> ranking = new HashMap<>();
        Optional<Rank> userRankOption = this.getScore(userId);

        if (userRankOption.isEmpty()) {
            return ranking;
        }

        Rank userRank = userRankOption.get();
        List<Rank> allRanks = rankRepository.findAll();
        Map<String, Integer> gameModeRankings = new HashMap<>();
        gameModeRankings.put("refactoring", calculateRank(userRank.getRefactoringScore(),
                allRanks.stream().map(Rank::getRefactoringScore).sorted(Comparator.reverseOrder()).collect(Collectors.toList())));
        gameModeRankings.put("check-smell", calculateRank(userRank.getCheckSmellScore(),
                allRanks.stream().map(Rank::getCheckSmellScore).sorted(Comparator.reverseOrder()).collect(Collectors.toList())));
        gameModeRankings.put("mission", calculateRank(userRank.getMissionsScore(),
                allRanks.stream().map(Rank::getMissionsScore).sorted(Comparator.reverseOrder()).collect(Collectors.toList())));

        Map<String, Integer> refactoringExerciseRankings = new HashMap<>();
        for (Map.Entry<String, Integer> entry : userRank.getBestRefactoringScores().entrySet()) {
            String exerciseId = entry.getKey();
            Integer userScore = entry.getValue();

            List<Integer> scores = allRanks.stream()
                    .map(rank -> rank.getBestRefactoringScores().getOrDefault(exerciseId, 0))
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());

            refactoringExerciseRankings.put(exerciseId, calculateRank(userScore, scores));
        }

        ranking.put("gameModeRankings", gameModeRankings);
        ranking.put("refactoringExerciseRankings", refactoringExerciseRankings);
        return ranking;
    }

    private int calculateRank(Integer userScore, List<Integer> sortedScores) {
        int rank = 1;
        for (Integer score : sortedScores) {
            if (userScore.equals(score)) {
                return rank;
            }
            rank++;
        }
        return rank;
    }

}
