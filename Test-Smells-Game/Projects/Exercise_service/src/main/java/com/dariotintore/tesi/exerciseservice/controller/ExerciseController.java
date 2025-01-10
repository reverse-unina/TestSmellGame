package com.dariotintore.tesi.exerciseservice.controller;

import com.dariotintore.tesi.exerciseservice.service.FileService;
import com.dariotintore.tesi.exerciseservice.entity.exercise.checksmell.CheckSmellExercise;
import com.dariotintore.tesi.exerciseservice.entity.exercise.refactoring.RefactoringExercise;
import com.dariotintore.tesi.exerciseservice.entity.learningcontent.LearningContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {
    @Autowired
    private FileService fileService;
    private static final Logger logger = LoggerFactory.getLogger(ExerciseController.class);

    private final String refactoringDB = "ExerciseDB/RefactoringGame/";
    private final String checkSmellDB = "ExerciseDB/CheckSmellGame/";
    private final String learningContentDB = "ExerciseDB/LearningContent/";

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

}
