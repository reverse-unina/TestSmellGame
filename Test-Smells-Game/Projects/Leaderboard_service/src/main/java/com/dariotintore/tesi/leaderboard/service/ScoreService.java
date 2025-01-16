package com.dariotintore.tesi.leaderboard.service;

import com.dariotintore.tesi.leaderboard.dto.PodiumDTO;
import com.dariotintore.tesi.leaderboard.entity.Score;
import com.dariotintore.tesi.leaderboard.respository.RankRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScoreService {
    @Autowired
    private RankRepository rankRepository;
    private static final Logger logger = LoggerFactory.getLogger(ScoreService.class);


    public Score createNewRank(String userName) {
        Score score = new Score();
        score.setUserName(userName);
        return rankRepository.save(score);
    }

    public Score updateMissionsScore(String userName, int score) {
        Score rank = rankRepository.findByUserName(userName)
                .orElseGet(() -> createNewRank(userName));

        rank.setMissionsScore(rank.getMissionsScore() + score);
        return rankRepository.save(rank);
    }

    public Score updateBestCheckSmellGameScore(String userName, String exerciseId, int score) {
        Score rank = rankRepository.findByUserName(userName).get();

        Integer currentScoreForExercise = rank.getBestCheckSmellScore().get(exerciseId);
        Integer updatedScoreForExercise = rank.getBestCheckSmellScore().merge(exerciseId, score, Math::max);

        if (currentScoreForExercise == null) {
            rank.setCheckSmellScore(rank.getCheckSmellScore() + updatedScoreForExercise);
        } else if (updatedScoreForExercise > currentScoreForExercise) {
            rank.setCheckSmellScore(rank.getCheckSmellScore() + updatedScoreForExercise - currentScoreForExercise);
        }

        return rankRepository.save(rank);
    }

    public Score updateBestRefactoringScore(String userName, String exerciseId, int score) {
        Score rank = rankRepository.findByUserName(userName).get();

        Integer currentScoreForExercise = rank.getBestRefactoringScores().get(exerciseId);
        Integer updatedScoreForExercise = rank.getBestRefactoringScores().merge(exerciseId, score, Math::max);

        if (currentScoreForExercise == null) {
            rank.setRefactoringScore(rank.getRefactoringScore() + updatedScoreForExercise);
        } else if (updatedScoreForExercise > currentScoreForExercise) {
            rank.setRefactoringScore(rank.getRefactoringScore() + updatedScoreForExercise - currentScoreForExercise);
        }

        return rankRepository.save(rank);
    }

    public Optional<Score> getScore(String userName) {
        return rankRepository.findByUserName(userName);
    }

    public Map<String, Object> getUserRank(String userName) {
        Map<String, Object> ranking = new HashMap<>();
        Optional<Score> userScore = this.getScore(userName);
        Score userRank;

        if (userScore.isEmpty()) {
            userRank = new Score();
            userRank.setUserName(userName);
        } else {
            userRank = userScore.get();
        }

        List<Score> allScores = rankRepository.findAll();

        Map<String, Integer> refactoringExerciseRankings = new HashMap<>();
        for (Map.Entry<String, Integer> entry : userRank.getBestRefactoringScores().entrySet()) {
            String exerciseId = entry.getKey();
            Integer exerciseScore = entry.getValue();

            List<Integer> scores = allScores.stream()
                    .map(score -> score.getBestRefactoringScores().getOrDefault(exerciseId, 0))
                    .sorted(Comparator.reverseOrder())
                    .distinct()
                    .collect(Collectors.toList());

            refactoringExerciseRankings.put(exerciseId, calculateRank(exerciseScore, scores));
        }

        Map<String, Integer> gameModeRankings = new HashMap<>();
        gameModeRankings.put("refactoring", calculateRank(userRank.getRefactoringScore(),
                allScores.stream().map(Score::getRefactoringScore).sorted(Comparator.reverseOrder()).distinct().collect(Collectors.toList())));
        gameModeRankings.put("check-smell", calculateRank(userRank.getCheckSmellScore(),
                allScores.stream().map(Score::getCheckSmellScore).sorted(Comparator.reverseOrder()).distinct().collect(Collectors.toList())));
        gameModeRankings.put("missions", calculateRank(userRank.getMissionsScore(),
                allScores.stream().map(Score::getMissionsScore).sorted(Comparator.reverseOrder()).distinct().collect(Collectors.toList())));


        ranking.put("gameModeRankings", gameModeRankings);
        ranking.put("refactoringExerciseRankings", refactoringExerciseRankings);
        return ranking;
    }

    public Map<String, List<PodiumDTO>> getGameModePodium(int podiumDimension) {
        Map<String, List<PodiumDTO>> topUsers = new HashMap<>();

        List<Score> allScores = rankRepository.findAll();
        logger.info("allScores: " + allScores);

        Map<String, List<Integer>> gameModeScores = new HashMap<>();
        gameModeScores.put("refactoring", allScores.stream().map(Score::getRefactoringScore).sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
        gameModeScores.put("check-smell", allScores.stream().map(Score::getCheckSmellScore).sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
        gameModeScores.put("missions", allScores.stream().map(Score::getMissionsScore).sorted(Comparator.reverseOrder()).collect(Collectors.toList()));

        for (Map.Entry<String, List<Integer>> entry : gameModeScores.entrySet()) {
            String mode = entry.getKey();
            List<Integer> sortedScores = entry.getValue();

            // Collect users for each score
            Map<Integer, List<String>> scoreToUsersMap = new HashMap<>();
            for (Score score : allScores) {
                int scoreValue = getScoreByMode(score, mode);
                scoreToUsersMap
                        .computeIfAbsent(scoreValue, k -> new ArrayList<>())
                        .add(score.getUserName());
            }

            logger.info("scoreToUsersMap: " + scoreToUsersMap);

            List<PodiumDTO> topModeUsers = sortedScores.stream()
                    .distinct()
                    .limit(podiumDimension)
                    .map(score -> {
                        List<String> usersWithScore = scoreToUsersMap.get(score);
                        return new PodiumDTO(usersWithScore, score);
                    })
                    .collect(Collectors.toList());

            topUsers.put(mode, topModeUsers);
        }

        return topUsers;
    }

    public Map<String, List<PodiumDTO>> getRefactoringExercisePodium(int podiumDimension) {
        List<Score> allScores = rankRepository.findAll();

        Map<String, List<PodiumDTO>> refactoringExerciseTopUsers = new HashMap<>();
        Set<String> exerciseIds = allScores.stream()
                .flatMap(score -> score.getBestRefactoringScores().keySet().stream())
                .collect(Collectors.toSet());

        for (String exerciseId : exerciseIds) {
            List<Integer> sortedScores = allScores.stream()
                    .map(score -> score.getBestRefactoringScores().getOrDefault(exerciseId, 0))
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());

            // Collect users for each score
            Map<Integer, List<String>> scoreToUsersMap = new HashMap<>();
            for (Score score : allScores) {
                int scoreValue = score.getBestRefactoringScores().getOrDefault(exerciseId, 0);
                scoreToUsersMap
                        .computeIfAbsent(scoreValue, k -> new ArrayList<>())
                        .add(score.getUserName());
            }

            // Select podium users
            List<PodiumDTO> topExerciseUsers = sortedScores.stream()
                    .distinct()
                    .limit(podiumDimension)
                    .map(score -> {
                        List<String> usersWithScore = scoreToUsersMap.get(score);
                        return new PodiumDTO(usersWithScore, score);
                    })
                    .collect(Collectors.toList());

            refactoringExerciseTopUsers.put(exerciseId, topExerciseUsers);
        }

        return refactoringExerciseTopUsers;
    }

    private Integer getScoreByMode(Score score, String mode) {
        switch (mode) {
            case "refactoring":
                return score.getRefactoringScore();
            case "check-smell":
                return score.getCheckSmellScore();
            case "missions":
                return score.getMissionsScore();
            default:
                throw new IllegalArgumentException("Invalid mode: " + mode);
        }
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
