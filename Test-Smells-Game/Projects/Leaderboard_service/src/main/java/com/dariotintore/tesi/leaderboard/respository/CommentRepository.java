package com.dariotintore.tesi.leaderboard.respository;

import com.dariotintore.tesi.leaderboard.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
