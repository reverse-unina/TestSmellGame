package com.dariotintore.tesi.leaderboard.service;

import com.dariotintore.tesi.leaderboard.controller.UserSubmitHistoryController;
import com.dariotintore.tesi.leaderboard.dto.UserSubmitHistoryDTO;
import com.dariotintore.tesi.leaderboard.entity.UserSubmitHistory;
import com.dariotintore.tesi.leaderboard.respository.UserSubmitHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserSubmitHistoryService {
    private final UserSubmitHistoryRepository userSubmitHistoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserSubmitHistoryService.class);


    public UserSubmitHistoryService(UserSubmitHistoryRepository userSubmitHistoryRepository) {
        this.userSubmitHistoryRepository = userSubmitHistoryRepository;
    }

    public UserSubmitHistory save(UserSubmitHistoryDTO userSubmitHistoryDTO) {
        UserSubmitHistory userSubmitHistory = new UserSubmitHistory();
        userSubmitHistory.setUserId(userSubmitHistoryDTO.getUserId());
        userSubmitHistory.setExerciseName(userSubmitHistoryDTO.getExerciseName());
        userSubmitHistory.setExerciseScore(userSubmitHistoryDTO.getExerciseScore());
        userSubmitHistory.setExerciseType(userSubmitHistoryDTO.getExerciseType());

        return userSubmitHistoryRepository.save(userSubmitHistory);
    }

    public List<UserSubmitHistory> findAllUserSubmitHistoryByUserId(Long userId) {
        return userSubmitHistoryRepository.findUserSubmitHistoriesByUserId(userId);
    }
}
