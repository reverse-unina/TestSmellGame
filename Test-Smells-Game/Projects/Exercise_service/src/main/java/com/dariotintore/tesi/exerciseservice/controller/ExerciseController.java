package com.dariotintore.tesi.exerciseservice.controller;

import com.dariotintore.tesi.exerciseservice.service.FileService;
import com.dariotintore.tesi.exerciseservice.entity.exercise.checksmell.CheckSmellExercise;
import com.dariotintore.tesi.exerciseservice.entity.exercise.refactoring.RefactoringExercise;
import com.dariotintore.tesi.exerciseservice.entity.learningcontent.LearningContent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {
    private final FileService fileService = new FileService();
    private static final Logger logger = LoggerFactory.getLogger(ExerciseController.class);
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String refactoringDB = "ExerciseDB/RefactoringGame/";
    private final String checkSmellDB = "ExerciseDB/CheckSmellGame/";
    private final String learningContentDB = "ExerciseDB/LearningContent/";
    private final String logDB = "/usr/src/app/assets/logs/";

    @GetMapping("/refactoring")
    public ResponseEntity<Object> getRefactoringExerciseList() {
        Object result = fileService.getAllJsonFilesInDirectory(refactoringDB, RefactoringExercise.class);

        if (result instanceof Map) {
            Map<HttpStatus, String> resultMap = (Map<HttpStatus, String>) result;
            
            Map.Entry<HttpStatus, String> entry = resultMap.entrySet().iterator().next(); 
            HttpStatus httpStatus = entry.getKey();  
            String error = entry.getValue();    

            return ResponseEntity.status(httpStatus).body(Map.of("message", error));
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/refactoring/{exerciseId}/{fileType}")
    public ResponseEntity<Object> getRefactoringExerciseById(
            @PathVariable("exerciseId") String exerciseId,
            @PathVariable("fileType") String fileType
    ) {
        Object result = fileService.getRefactoringExerciseFile(exerciseId, fileType);

        if (result instanceof Map) {
            Map<HttpStatus, String> resultMap = (Map<HttpStatus, String>) result;

            Map.Entry<HttpStatus, String> entry = resultMap.entrySet().iterator().next();

            HttpStatus httpStatus = entry.getKey();
            String error = entry.getValue();

            return ResponseEntity.status(httpStatus).body(Map.of("message", error));
        } else {
            switch (fileType) {
                case "Production":
                case "Test":
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + exerciseId + "\"")
                            .body(result);
                default:
                    return ResponseEntity.ok(result);
            }
        }
    }

    @GetMapping("/checksmell")
    public ResponseEntity<Object> getCheckSmellExerciseList() {
        Object result = fileService.getAllJsonFilesInDirectory(checkSmellDB, CheckSmellExercise.class);

        if (result instanceof Map) {
            Map<HttpStatus, String> resultMap = (Map<HttpStatus, String>) result;

            Map.Entry<HttpStatus, String> entry = resultMap.entrySet().iterator().next();
            HttpStatus httpStatus = entry.getKey();
            String error = entry.getValue();

            return ResponseEntity.status(httpStatus).body(Map.of("message", error));
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/checksmell/{exerciseId}")
    public ResponseEntity<Object> getCheckSmellExerciseById(@PathVariable("exerciseId") String exerciseId) {
        Object result = fileService.getJsonFileById(exerciseId, checkSmellDB, CheckSmellExercise.class);

        if (result instanceof Map) {
            Map<HttpStatus, String> resultMap = (Map<HttpStatus, String>) result;

            Map.Entry<HttpStatus, String> entry = resultMap.entrySet().iterator().next();
            HttpStatus httpStatus = entry.getKey();
            String error = entry.getValue();

            return ResponseEntity.status(httpStatus).body(Map.of("message", error));
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/learning/{learningId}")
    public ResponseEntity<Object> retrieveLearningContent(@PathVariable String learningId) {
        Object result = fileService.getJsonFileById(learningId, learningContentDB, LearningContent.class);

        if (result instanceof Map) {
            Map<HttpStatus, String> resultMap = (Map<HttpStatus, String>) result;

            Map.Entry<HttpStatus, String> entry = resultMap.entrySet().iterator().next();
            HttpStatus httpStatus = entry.getKey();
            String error = entry.getValue();

            return ResponseEntity.status(httpStatus).body(Map.of("message", error));
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @PostMapping("/refactoring/submit/")
    public ResponseEntity<Object> submitRefactoringAssignment(
            @RequestParam("gameMode") String gameMode,
            @RequestParam("studentName") String studentName,
            @RequestParam("exerciseId") String exerciseId,
            @RequestParam("productionCode") MultipartFile productionCode,
            @RequestParam("testCode") MultipartFile testCode,
            @RequestParam("shellCode") MultipartFile shellCode,
            @RequestParam("results") MultipartFile results) {

        // Task to be executed in the background
        Callable<ResponseEntity<Object>> task = () -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = LocalDateTime.now().format(formatter);

            String studentDirectoryPath = logDB + gameMode + "/" + exerciseId + "/" + studentName + "/" + currentDateTime + "/";
            try {
                // Create directory if it doesn't exist
                File studentDirectory = new File(studentDirectoryPath);
                if (!studentDirectory.exists()) {
                    studentDirectory.mkdirs();
                }

                // Save uploaded files
                productionCode.transferTo(new File(studentDirectoryPath + studentName + "_ClassCode.java"));
                testCode.transferTo(new File(studentDirectoryPath + studentName + "_TestCode.java"));
                shellCode.transferTo(new File(studentDirectoryPath + studentName + "_ShellCode.java"));
                results.transferTo(new File(studentDirectoryPath + studentName + "_results.txt"));

                logger.info("Processing completed for student '{}'", studentName);
                return ResponseEntity.ok("Files uploaded successfully");
            } catch (IOException e) {
                logger.error("Error uploading files or updating assignment", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading files or updating assignment");
            }
        };

        // Execute the task in the executor thread
        try {
            Future<ResponseEntity<Object>> future = executorService.submit(task);
            return future.get(); // Wait for the task to complete
        } catch (Exception e) {
            logger.error("Error processing request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request");
        }
    }

    @PostMapping("/checksmell/submit/")
    public ResponseEntity<Object> submitCheckSmellAssignment(
            @RequestParam("gameMode") String assignmentId,
            @RequestParam("studentName") String studentName,
            @RequestParam("exerciseId") String exerciseId,
            @RequestParam("results") MultipartFile results) {

        // Task to be executed in the background
        Callable<ResponseEntity<Object>> task = () -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = LocalDateTime.now().format(formatter);

            String studentDirectoryPath = logDB + assignmentId + "/" + exerciseId + "/" + studentName + "/" + currentDateTime + "/";
            try {
                // Create directory if it doesn't exist
                File studentDirectory = new File(studentDirectoryPath);
                if (!studentDirectory.exists()) {
                    studentDirectory.mkdirs();
                }

                // Save uploaded files
                results.transferTo(new File(studentDirectoryPath + studentName + "_results.txt"));

                logger.info("Processing completed for student '{}'", studentName);
                return ResponseEntity.ok("Files uploaded and assignment updated successfully");

            } catch (IOException e) {
                logger.error("Error uploading files or updating assignment", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading files or updating assignment");
            }
        };

        // Execute the task in the executor thread
        try {
            Future<ResponseEntity<Object>> future = executorService.submit(task);
            return future.get(); // Wait for the task to complete
        } catch (Exception e) {
            logger.error("Error processing request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request");
        }
    }

}
