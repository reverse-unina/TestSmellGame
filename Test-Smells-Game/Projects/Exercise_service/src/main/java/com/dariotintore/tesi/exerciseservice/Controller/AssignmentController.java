package com.dariotintore.tesi.exerciseservice.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.dariotintore.tesi.exerciseservice.Assignment.Assignment;
import com.dariotintore.tesi.exerciseservice.Assignment.Student;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    private final String assignmentsDirectory = "/usr/src/app/assets/assignments/";
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static final Logger logger = LoggerFactory.getLogger(AssignmentController.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/get-assignments")
    public ResponseEntity<List<Assignment>> getAssignments() {
        List<Assignment> assignments = new ArrayList<>();
        File dir = new File(assignmentsDirectory);
        
        if (!dir.exists() || !dir.isDirectory()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
        if (files == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
        try {
            for (File file : files) {
                Assignment assignment = objectMapper.readValue(file, Assignment.class);
                assignments.add(assignment);
            }
        } catch (IOException e) {
            logger.error("Error reading assignment files", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(assignments);
    }

    @PostMapping("/submit-assignment")
    public ResponseEntity<String> submitAssignment(
            @RequestParam("assignmentName") String assignmentName,
            @RequestParam("studentName") String studentName,
            @RequestParam("productionCode") MultipartFile productionCode,
            @RequestParam("testCode") MultipartFile testCode,
            @RequestParam("shellCode") MultipartFile shellCode,
            @RequestParam("results") MultipartFile results) {

        // Task to be executed in the background
        Callable<ResponseEntity<String>> task = () -> {
            String studentDirectoryPath = assignmentsDirectory + assignmentName + "/" + studentName + "/";
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
                String filePath = assignmentsDirectory + assignmentName + ".json";
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
                return ResponseEntity.ok("Files uploaded and assignment updated successfully");

            } catch (IOException e) {
                logger.error("Error uploading files or updating assignment", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading files or updating assignment");
            }
        };

        // Execute the task in the executor thread
        try {
            Future<ResponseEntity<String>> future = executorService.submit(task);
            return future.get(); // Wait for the task to complete
        } catch (Exception e) {
            logger.error("Error processing request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request");
        }
    }
}
