package com.dariotintore.tesi.userservice.controller;


import com.dariotintore.tesi.userservice.dto.user.TestResultDTO;
import com.dariotintore.tesi.userservice.entity.TestResult;
import com.dariotintore.tesi.userservice.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test-results")
public class TestResultController {
    @Autowired
    private TestResultService testService;

    @PostMapping("/{userId}")
    public ResponseEntity<TestResult> saveTestResult(@PathVariable Long userId, @RequestBody TestResultDTO testResultDto) {
        System.out.println("Dati ricevuti: " + testResultDto.toString());
        TestResult testResult = testService.saveTestResult(userId, testResultDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(testResult);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TestResult>> getTestResults(@PathVariable Long userId) {
        List<TestResult> testResults = testService.getTestResults(userId);
        return ResponseEntity.ok(testResults);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteTestResults(@PathVariable Long userId){
        testService.deleteByUserId(userId);
        return ResponseEntity.noContent().build();
    }
}

