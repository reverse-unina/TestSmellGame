package com.dariotintore.tesi.exerciseservice.controller;

import com.dariotintore.tesi.exerciseservice.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.dariotintore.tesi.exerciseservice.entity.assignment.Assignment;
import com.dariotintore.tesi.exerciseservice.entity.assignment.Student;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {
    @Autowired
    private FileService fileService;

    private final String assignmentDB = "/usr/src/app/assets/assignments/";
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static final Logger logger = LoggerFactory.getLogger(AssignmentController.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/")
    public ResponseEntity<Object> getAssignments() {
        Object result = fileService.getAllJsonFilesInDirectory(assignmentDB, Assignment.class);

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

    @PostMapping("/submit/refactoring")
    public ResponseEntity<Object> submitRefactoringAssignment(
            @RequestParam("assignmentId") String assignmentId,
            @RequestParam("studentName") String studentName,
            @RequestParam("exerciseId") String exerciseId,
            @RequestParam("productionCode") MultipartFile productionCode,
            @RequestParam("testCode") MultipartFile testCode,
            @RequestParam("shellCode") MultipartFile shellCode,
            @RequestParam("results") MultipartFile results) {

        // Task to be executed in the background
        Callable<ResponseEntity<Object>> task = () -> {
            String studentDirectoryPath = assignmentDB + assignmentId + "/" + exerciseId + "/" + studentName + "/";
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

                // Update assignment status
                FileService fileService = new FileService();
                Object path = fileService.getAssignmentFilePathById(assignmentId, assignmentDB);

                if (path instanceof Map) {
                    Map<HttpStatus, String> resultMap = (Map<HttpStatus, String>) path;

                    Map.Entry<HttpStatus, String> entry = resultMap.entrySet().iterator().next();
                    HttpStatus httpStatus = entry.getKey();
                    String error = entry.getValue();

                    return ResponseEntity.status(httpStatus).body(Map.of("message", error));
                }

                String filePath = path.toString();
                System.out.println(filePath);
                File assignmentFile = new File(filePath);
                if (!assignmentFile.exists()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Assignment not found"));
                }

                Assignment assignment = objectMapper.readValue(assignmentFile, Assignment.class);
                boolean studentFound = false;

                for (Student student : assignment.getStudents()) {
                    if (student.getName().equals(studentName)) {
                        student.setSubmitted(true);
                        studentFound = true;
                        break;
                    }
                }

                if (!studentFound) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
                }

                // Write update to file
                objectMapper.writeValue(assignmentFile, assignment);
                logger.info("Processing completed for student '{}'", studentName);
                return ResponseEntity.ok(Map.of("message", "Files uploaded and assignment updated successfully"));

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

    @PostMapping("/submit/checksmell")
    public ResponseEntity<Object> submitCheckSmellAssignment(
            @RequestParam("assignmentId") String assignmentId,
            @RequestParam("studentName") String studentName,
            @RequestParam("exerciseId") String exerciseId,
            @RequestParam("results") MultipartFile results) {

        // Task to be executed in the background
        Callable<ResponseEntity<Object>> task = () -> {
            String studentDirectoryPath = assignmentDB + assignmentId + "/" + exerciseId + "/" + studentName + "/";
            try {
                // Create directory if it doesn't exist
                File studentDirectory = new File(studentDirectoryPath);
                if (!studentDirectory.exists()) {
                    studentDirectory.mkdirs();
                }

                // Update assignment status
                FileService fileService = new FileService();
                Object path = fileService.getAssignmentFilePathById(assignmentId, assignmentDB);

                if (path instanceof Map) {
                    Map<HttpStatus, String> resultMap = (Map<HttpStatus, String>) path;

                    Map.Entry<HttpStatus, String> entry = resultMap.entrySet().iterator().next();
                    HttpStatus httpStatus = entry.getKey();
                    String error = entry.getValue();

                    return ResponseEntity.status(httpStatus).body(Map.of("message", error));
                }

                String filePath = path.toString();

                // Save uploaded files
                results.transferTo(new File(studentDirectoryPath + studentName + "_results.txt"));

                // Update assignment status
                File assignmentFile = new File(filePath);
                if (!assignmentFile.exists()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assignment not found");
                }

                Assignment assignment = objectMapper.readValue(assignmentFile, Assignment.class);
                boolean studentFound = false;

                for (Student student : assignment.getStudents()) {
                    if (student.getName().equals(studentName)) {
                        student.setSubmitted(true);
                        studentFound = true;
                        break;
                    }
                }

                if (!studentFound) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
                }

                // Write update to file
                objectMapper.writeValue(assignmentFile, assignment);
                logger.info("Processing completed for student '{}'", studentName);
                return ResponseEntity.ok(Map.of("message", "Files uploaded and assignment updated successfully"));

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
