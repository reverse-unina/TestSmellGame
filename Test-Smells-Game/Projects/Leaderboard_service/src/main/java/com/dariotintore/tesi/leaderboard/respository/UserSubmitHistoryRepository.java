package com.dariotintore.tesi.leaderboard.respository;

import com.dariotintore.tesi.leaderboard.entity.UserSubmitHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSubmitHistoryRepository extends JpaRepository<UserSubmitHistory,Long> {
    @Query(value = "SELECT * FROM user_submit_history H WHERE H.user_id = ?1 ORDER BY H.date_time ASC ", nativeQuery = true)
    //@Query(value = "SELECT * FROM user_submit_history H ORDER BY H.date_time ASC ", nativeQuery = true)
    List<UserSubmitHistory> findUserSubmitHistoriesByUserId(Long userId);
}
