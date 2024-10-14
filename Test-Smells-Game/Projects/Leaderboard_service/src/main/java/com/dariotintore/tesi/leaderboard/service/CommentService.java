package com.dariotintore.tesi.leaderboard.service;

import com.dariotintore.tesi.leaderboard.entity.Comment;
import com.dariotintore.tesi.leaderboard.respository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    public void saveCommentForExerciseName(Comment comment) {
        commentRepository.save(comment);
    }

}
