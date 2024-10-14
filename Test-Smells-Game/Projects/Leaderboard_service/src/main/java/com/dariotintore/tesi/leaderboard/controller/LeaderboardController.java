package com.dariotintore.tesi.leaderboard.controller;

import com.dariotintore.tesi.leaderboard.dto.SolutionSaveRequestDTO;
import com.dariotintore.tesi.leaderboard.entity.Comment;
import com.dariotintore.tesi.leaderboard.entity.Solution;
import com.dariotintore.tesi.leaderboard.entity.VoteType;
import com.dariotintore.tesi.leaderboard.service.CommentService;
import com.dariotintore.tesi.leaderboard.service.SolutionService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

  @Autowired
  SolutionService solutionService;

  @Autowired
  CommentService commentService;


    @GetMapping("/{exerciseId}")
    public List<Solution> getSolutionsByExercise(@PathVariable("exerciseId") String exerciseId){
      return solutionService.getSolutionsByExerciseName(exerciseId);
    }

    @PostMapping("solution/postComment")
    public void postCommentByExerciseName(@RequestBody Comment comment){
      commentService.saveCommentForExerciseName(comment);
    }

    @PostMapping("/")
    public void saveSolutionByExercise(@RequestBody SolutionSaveRequestDTO solution){
      solutionService.saveSolutionForExerciseName(solution);
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
