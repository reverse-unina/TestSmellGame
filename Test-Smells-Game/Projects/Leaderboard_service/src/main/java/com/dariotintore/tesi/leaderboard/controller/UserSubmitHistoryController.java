package com.dariotintore.tesi.leaderboard.controller;

import com.dariotintore.tesi.leaderboard.dto.UserSubmitHistoryDTO;
import com.dariotintore.tesi.leaderboard.entity.UserSubmitHistory;
import com.dariotintore.tesi.leaderboard.service.UserSubmitHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
public class UserSubmitHistoryController {
    private final UserSubmitHistoryService userSubmitHistoryService;

    private static final Logger logger = LoggerFactory.getLogger(UserSubmitHistoryController.class);


    public UserSubmitHistoryController(UserSubmitHistoryService userSubmitHistoryService) {
        this.userSubmitHistoryService = userSubmitHistoryService;
    }

    @PostMapping("/save")
    public ResponseEntity<UserSubmitHistory> addNewUserSubmitHistory(@RequestBody UserSubmitHistoryDTO userSubmitHistoryDTO) {
        return ResponseEntity.ok(this.userSubmitHistoryService.save(userSubmitHistoryDTO));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserSubmitHistory>> getAllUserSubmitHistoryByUserId(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(this.userSubmitHistoryService.findAllUserSubmitHistoryByUserId(userId));
    }
}
