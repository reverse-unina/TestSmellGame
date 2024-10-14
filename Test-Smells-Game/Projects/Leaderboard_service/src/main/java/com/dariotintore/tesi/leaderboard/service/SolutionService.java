package com.dariotintore.tesi.leaderboard.service;


import com.dariotintore.tesi.leaderboard.dto.SolutionSaveRequestDTO;
import com.dariotintore.tesi.leaderboard.entity.Solution;
import com.dariotintore.tesi.leaderboard.entity.UserSolution;
import com.dariotintore.tesi.leaderboard.entity.UserVotes;
import com.dariotintore.tesi.leaderboard.entity.VoteType;
import com.dariotintore.tesi.leaderboard.respository.SolutionRepository;
import com.dariotintore.tesi.leaderboard.respository.VoteRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.dariotintore.tesi.leaderboard.entity.VoteType.DOWN;
import static com.dariotintore.tesi.leaderboard.entity.VoteType.UP;

@Service
public class SolutionService {

    @Autowired
    SolutionRepository solutionRepository;

    @Autowired
    VoteRepository voteRepository;


    public void saveSolutionForExerciseName(SolutionSaveRequestDTO solutionDTO) {
        Solution solution = Solution.builder()
                .playerName(solutionDTO.getPlayerName())
                .exerciseId(solutionDTO.getExerciseId())
                .refactoredCode(solutionDTO.getRefactoredCode())
                .score(solutionDTO.getScore())
                .refactoringResult(solutionDTO.isRefactoringResult())
                .originalCoverage(solutionDTO.getOriginalCoverage())
                .refactoredCoverage(solutionDTO.getRefactoredCoverage())
                .smells(solutionDTO.getSmells())
                .build();
        solutionRepository.save(solution);
    }

    public List<Solution> getSolutionsByExerciseName(String exerciseId) {
        return solutionRepository.findSolutionsByExerciseId(exerciseId);
    }

    public void upVoteSolution(Long solutionId) {
        Optional<Solution> solution = solutionRepository.findById(solutionId);
        if(solution.isPresent()){
            Solution s = solution.get();
            s.setUpVotes(s.getUpVotes() + 1);
            solutionRepository.save(s);
        }
    }

    public void downVoteSolution(Long solutionId) {
        Optional<Solution> solution = solutionRepository.findById(solutionId);
        if(solution.isPresent()){
            Solution s = solution.get();
            s.setDownVotes(s.getDownVotes() + 1);
            solutionRepository.save(s);
        }
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
        Optional<Solution> solution = solutionRepository.findById(solutionId);
        if(solution.isPresent()){
        Solution s = solution.get();
            s.setUpVotes(Math.toIntExact(upvotesNumber));
            s.setDownVotes(Math.toIntExact(downvotesNumber));
            solutionRepository.save(s);
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
