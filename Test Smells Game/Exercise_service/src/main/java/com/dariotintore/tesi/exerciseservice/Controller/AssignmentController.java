package com.dariotintore.tesi.exerciseservice.Controller;

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

@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    private final String assignmentsDirectory = "/usr/src/app/assets/assignments/";

    @GetMapping("/get-assignments")
    public ResponseEntity<List<Assignment>> getAssignments() {
        ObjectMapper objectMapper = new ObjectMapper();
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
            e.printStackTrace();
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

        String studentDirectoryPath = assignmentsDirectory + assignmentName + "/" + studentName + "/";

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File studentDirectory = new File(studentDirectoryPath);
            if (!studentDirectory.exists()) {
                studentDirectory.mkdirs();
            }

            // Save production code file
            File productionFile = new File(studentDirectoryPath + studentName + "_ClassCode.java");
            productionCode.transferTo(productionFile);

            // Save test code file
            File testFile = new File(studentDirectoryPath + studentName + "_TestCode.java");
            testCode.transferTo(testFile);

            // Save shell code file
            File shellFile = new File(studentDirectoryPath + studentName + "_ShellCode.java");
            shellCode.transferTo(shellFile);

            // Save results file
            File resultsFile = new File(studentDirectoryPath + studentName + "_results.txt");
            results.transferTo(resultsFile);

            // Update assignment status
            String filePath = assignmentsDirectory + assignmentName + ".json";
            File assignmentFile = new File(filePath);
            if (!assignmentFile.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assignment not found");
            }

            Assignment assignment = objectMapper.readValue(assignmentFile, Assignment.class);
            boolean studentFound = false;

            for (Student student : assignment.getStudenti()) {
                if (student.getNome().equals(studentName)) {
                    student.setConsegnato(true);
                    studentFound = true;
                    break;
                }
            }

            if (!studentFound) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
            }

            objectMapper.writeValue(assignmentFile, assignment);

            return ResponseEntity.ok("Files uploaded and assignment updated successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading files or updating assignment");
        }
    }
}


