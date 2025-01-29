package com.dariotintore.tesi.userservice.repository;

import com.dariotintore.tesi.userservice.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    List<TestResult> findByUser_UserId(Long userId);

    @Modifying
    @Query("DELETE FROM TestResult t WHERE t.user.userId = :userId")
    void deleteByUserId(Long userId);
}