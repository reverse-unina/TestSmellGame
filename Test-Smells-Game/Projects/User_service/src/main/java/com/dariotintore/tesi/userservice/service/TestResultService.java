package com.dariotintore.tesi.userservice.service;

import com.dariotintore.tesi.userservice.dto.user.TestResultDTO;
import com.dariotintore.tesi.userservice.entity.TestResult;
import com.dariotintore.tesi.userservice.entity.User;
import com.dariotintore.tesi.userservice.repository.TestResultRepository;
import com.dariotintore.tesi.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TestResultService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestResultRepository testResultRepository;


    public TestResult saveTestResult(Long userId, TestResultDTO testResultDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + userId));

        TestResult testResult = new TestResult();
        testResult.setUser(user);
        testResult.setRefactoringScore(testResultDto.getRefactoringScore());
        testResult.setCheckScore(testResultDto.getCheckScore());
        testResult.setTotalScore(testResultDto.getTotalScore());
        testResult.setCorrectAnswers(testResultDto.getCorrectAnswers());
        testResult.setWrongAnswers(testResultDto.getWrongAnswers());
        testResult.setCompletionTime(testResultDto.getCompletionTime());
        testResult.setDate(testResultDto.getDate());

        return testResultRepository.save(testResult);
    }

    public List<TestResult> getTestResults(Long userId) {
        return testResultRepository.findByUser_UserId(userId);
    }


    @Transactional
    public void deleteByUserId(Long userId) {
        testResultRepository.deleteByUserId(userId);
    }
}