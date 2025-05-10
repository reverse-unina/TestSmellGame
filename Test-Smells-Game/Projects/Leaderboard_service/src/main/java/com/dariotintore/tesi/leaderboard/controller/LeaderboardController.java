package com.dariotintore.tesi.leaderboard.controller;

import com.dariotintore.tesi.leaderboard.dto.CheckSmellSolutionDTO;
import com.dariotintore.tesi.leaderboard.dto.RefactoringSolutionDTO;
import com.dariotintore.tesi.leaderboard.entity.CheckSmellSolution;
import com.dariotintore.tesi.leaderboard.entity.Comment;
import com.dariotintore.tesi.leaderboard.entity.RefactoringSolution;
import com.dariotintore.tesi.leaderboard.entity.VoteType;
import com.dariotintore.tesi.leaderboard.service.CommentService;
import com.dariotintore.tesi.leaderboard.service.SolutionService;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    private final SolutionService solutionService;
    private final CommentService commentService;

    // Constructor Injection nel controller
    public LeaderboardController(SolutionService solutionService, CommentService commentService) {
        this.solutionService = solutionService;
        this.commentService = commentService;
    }

    @GetMapping("/refactoring/{exerciseId}")
    public List<RefactoringSolution> getRefactoringSolutionsByExerciseId(@PathVariable("exerciseId") String exerciseId){
      return solutionService.getRefactoringSolution(exerciseId);
    }

    @GetMapping("/checksmell/{exerciseId}")
    public List<CheckSmellSolution> getCheckSmellSolutionsByExerciseId(@PathVariable("exerciseId") String exerciseId){
        return solutionService.getCheckSmellSolution(exerciseId);
    }

    @PostMapping("solution/postComment")
    public void postCommentByExerciseName(@RequestBody Comment comment){
      commentService.saveCommentForExerciseName(comment);
    }

    @PostMapping("/refactoring")
    public ResponseEntity<RefactoringSolution> saveRefactoringSolutionByExercise(@RequestBody RefactoringSolutionDTO solution){
      return ResponseEntity.ok(solutionService.saveSolutionForExerciseName(solution));
    }

    @PostMapping("/checksmell")
    public ResponseEntity<CheckSmellSolution> saveCheckSmellSolutionByExerciseId(@RequestBody CheckSmellSolutionDTO solution){
        return ResponseEntity.ok(solutionService.saveCheckSmellSolution(solution));
    }

    @PostMapping("solution/{solutionId}")
    public void voteSolution(@PathVariable("solutionId") Long id, @RequestBody JSONObject body){
      solutionService.voteSolution(id, body);
    }

    @GetMapping("solution/{solutionId}/{userId}")
    public VoteType getVote(@PathVariable("solutionId") Long solutionId, @PathVariable("userId") Long userId){
      return solutionService.getVoteForUser(solutionId, userId);
    }

}
