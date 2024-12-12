package com.dariotintore.tesi.exerciseservice.Service;

import com.dariotintore.tesi.exerciseservice.Assignment.Assignment;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AssignmentService {

    public static boolean hasDuplicateNames(List<Assignment> assignments) {
        Set<String> nameSet = new HashSet<>();
        for (Assignment assignment : assignments) {
            if (!nameSet.add(assignment.getName())) {
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
