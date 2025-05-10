package com.dariotintore.tesi.exerciseservice.service;

import com.dariotintore.tesi.exerciseservice.entity.assignment.Assignment;
import com.dariotintore.tesi.exerciseservice.entity.exercise.checksmell.CheckSmellExercise;
import com.dariotintore.tesi.exerciseservice.entity.exercise.refactoring.RefactoringExercise;
import com.dariotintore.tesi.exerciseservice.entity.mission.Mission;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class JsonCheckService {

    public static boolean assignmentsHaveDuplicateNames(List<Assignment> assignments) {
        Set<String> nameSet = new HashSet<>();
        for (Assignment assignment : assignments) {
            if (!nameSet.add(assignment.getAssignmentId())) {
                return true;
            }
        }

        return false;
    }

    public static boolean missionsHaveDuplicateIds(List<Mission> missions) {
        Set<String> nameSet = new HashSet<>();
        for (Mission mission : missions) {
            if (!nameSet.add(mission.getId())) {
                return true;
            }
        }

        return false;
    }

    public static boolean refactoringExerciseHaveDuplicateIds(List<RefactoringExercise> exercises) {
        Set<String> nameSet = new HashSet<>();
        for (RefactoringExercise exercise : exercises) {
            if (!nameSet.add(exercise.getExerciseId())) {
                return true;
            }
        }

        return false;
    }

    public static boolean checkSmellExerciseHaveDuplicateIds(List<CheckSmellExercise> exercises) {
        Set<String> nameSet = new HashSet<>();
        for (CheckSmellExercise exercise : exercises) {
            if (!nameSet.add(exercise.getExerciseId())) {
                return true;
            }
        }

        return false;
    }

    public static String extractUnrecognizedField(String error) {
        if (error != null && error.startsWith("Unrecognized field")) {
            int startIndex = error.indexOf("\"") + 1;
            int endIndex = error.indexOf("\"", startIndex);
            if (startIndex > 0 && endIndex > startIndex) {
                return error.substring(startIndex, endIndex);
            }
        }
        return null;
    }

    public static String extractMissingRequiredField(String error) {
        if (error != null && error.startsWith("Missing required creator property")) {
            int startIndex = error.indexOf("'") + 1;
            int endIndex = error.indexOf("'", startIndex);
            if (startIndex > 0 && endIndex > startIndex) {
                return error.substring(startIndex, endIndex);
            }
        }
        return null;
    }


}
