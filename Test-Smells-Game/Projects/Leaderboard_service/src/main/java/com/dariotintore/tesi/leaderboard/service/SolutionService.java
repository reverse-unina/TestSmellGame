package com.dariotintore.tesi.leaderboard.service;


import com.dariotintore.tesi.leaderboard.dto.CheckSmellSolutionDTO;
import com.dariotintore.tesi.leaderboard.dto.RefactoringSolutionDTO;
import com.dariotintore.tesi.leaderboard.entity.CheckSmellSolution;
import com.dariotintore.tesi.leaderboard.entity.RefactoringSolution;
import com.dariotintore.tesi.leaderboard.entity.UserSolution;
import com.dariotintore.tesi.leaderboard.entity.VoteType;
import com.dariotintore.tesi.leaderboard.respository.CheckSmellSolutionRepository;
import com.dariotintore.tesi.leaderboard.respository.RefactoringSolutionRepository;
import com.dariotintore.tesi.leaderboard.respository.VoteRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SolutionService {

    RefactoringSolutionRepository refactoringSolutionRepository;
    CheckSmellSolutionRepository checkSmellSolutionRepository;
    VoteRepository voteRepository;

    @Autowired
    public SolutionService(RefactoringSolutionRepository refactoringSolutionRepository, CheckSmellSolutionRepository checkSmellSolutionRepository, VoteRepository voteRepository) {
        this.refactoringSolutionRepository = refactoringSolutionRepository;
        this.checkSmellSolutionRepository = checkSmellSolutionRepository;
        this.voteRepository = voteRepository;
    }

    public void saveSolutionForExerciseName(RefactoringSolutionDTO solutionDTO) {
        RefactoringSolution refactoringSolution = RefactoringSolution.builder()
                .playerName(solutionDTO.getPlayerName())
                .exerciseId(solutionDTO.getExerciseId())
                .refactoredCode(solutionDTO.getRefactoredCode())
                .score(solutionDTO.getScore())
                .refactoringResult(solutionDTO.isRefactoringResult())
                .originalCoverage(solutionDTO.getOriginalCoverage())
                .refactoredCoverage(solutionDTO.getRefactoredCoverage())
                .smells(solutionDTO.getSmells())
                .build();
        refactoringSolutionRepository.save(refactoringSolution);
    }

    public CheckSmellSolution saveCheckSmellSolution(CheckSmellSolutionDTO solutionDTO) {
        Optional<CheckSmellSolution> checkSmellSolutionOptional = checkSmellSolutionRepository.findSolutionByUsernameAndExerciseId(solutionDTO.getPlayerName(), solutionDTO.getExerciseId());

        if (checkSmellSolutionOptional.isPresent()) {
            CheckSmellSolution checkSmellSolution = checkSmellSolutionOptional.get();

            if (checkSmellSolution.getCorrectAnswers() < solutionDTO.getCorrectAnswers()) {
                checkSmellSolution.setCorrectAnswers(solutionDTO.getCorrectAnswers());
            }
            if (checkSmellSolution.getWrongAnswers() > solutionDTO.getWrongAnswers()) {
                checkSmellSolution.setWrongAnswers(solutionDTO.getWrongAnswers());
            }
            if (checkSmellSolution.getMissedAnswers() > solutionDTO.getMissedAnswers()) {
                checkSmellSolution.setMissedAnswers(solutionDTO.getMissedAnswers());
            }
            if (checkSmellSolution.getScore() < solutionDTO.getScore()) {
                checkSmellSolution.setScore(solutionDTO.getScore());
            }

            return checkSmellSolutionRepository.save(checkSmellSolution);
        } else {
            CheckSmellSolution checkSmellSolution = CheckSmellSolution.builder()
                    .playerName(solutionDTO.getPlayerName())
                    .exerciseId(solutionDTO.getExerciseId())
                    .score(solutionDTO.getScore())
                    .correctAnswers(solutionDTO.getCorrectAnswers())
                    .wrongAnswers(solutionDTO.getWrongAnswers())
                    .missedAnswers(solutionDTO.getMissedAnswers())
                    .build();

            return checkSmellSolutionRepository.save(checkSmellSolution);
        }
    }

    public List<RefactoringSolution> getRefactoringSolution(String exerciseId) {
        return refactoringSolutionRepository.findSolutionsByExerciseId(exerciseId);
    }

    public List<CheckSmellSolution> getCheckSmellSolution(String exerciseId) {
        return checkSmellSolutionRepository.findSolutionsByExerciseId(exerciseId);
    }

    public void voteSolution(Long solutionId, JSONObject body) {
        Integer i = (Integer) body.get("userId");
        Long userId = i.longValue();
        String vote = (String) body.get("voteType");
        VoteType voteType = VoteType.valueOf(vote);
        // GENERATE VOTE RECORD
        UserSolution userSolution = UserSolution.builder()
                                        .userId(userId)
                                        .solutionId(solutionId)
                                        .voteType(voteType)
                                        .build();
        voteRepository.save(userSolution);
        // UPDATE VOTE FOR SOLUTION
        Long upvotesNumber = voteRepository.getUpvotesNumber(solutionId);
        Long downvotesNumber = voteRepository.getDownvotesNumber(solutionId);
        Optional<RefactoringSolution> solution = refactoringSolutionRepository.findById(solutionId);
        if(solution.isPresent()){
        RefactoringSolution s = solution.get();
            s.setUpVotes(Math.toIntExact(upvotesNumber));
            s.setDownVotes(Math.toIntExact(downvotesNumber));
            refactoringSolutionRepository.save(s);
        }
    }

    public VoteType getVoteForUser(Long solutionId, Long userId) {
        Optional<UserSolution> userVote = voteRepository.findByUserAndSolutionId(solutionId, userId);
        if (userVote.isPresent()){
            UserSolution vote = userVote.get();
            return vote.getVoteType();
        }
        return null;
    }
}
