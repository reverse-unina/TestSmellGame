package com.dariotintore.tesi.exerciseservice.Controller;

import com.dariotintore.tesi.exerciseservice.Service.AssignmentService;
import com.dariotintore.tesi.exerciseservice.entity.mission.Mission;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/missions")
public class MissionController {
    private final String missionDirectory = "/usr/src/app/assets/missions/";
    private static final Logger logger = LoggerFactory.getLogger(MissionController.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/")
    public ResponseEntity<Object> getMissions() {
        List<Mission> missions = new ArrayList<>();
        File dir = new File(missionDirectory);

        if (!dir.exists() || !dir.isDirectory()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
        if (files == null) {
            logger.error("No missions files found");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "No missions found"));
        }

        for (File file : files) {
            try {
                Mission mission = objectMapper.readValue(file, Mission.class);
                missions.add(mission);
            } catch (UnrecognizedPropertyException e) {
                String error = "Unrecognized field \"" + AssignmentService.extractUnrecognizedField(e.getMessage()) + "\" not marked as ignorable found in file " + file.getName();
                logger.error("{}: {}", error, e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
            } catch (MismatchedInputException e) {
                String error = "Missing required property \"" + AssignmentService.extractMissingRequiredField(e.getMessage()) + "\" in file " + file.getName();
                logger.error("{}: {}", error, e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
            } catch (IOException e) {
                String error = "Error reading mission file " + file.getName();
                logger.error("{}: {}", error, e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
            }
        }

        /*
        if (AssignmentService.hasDuplicateNames(missions)) {
            String error = "Duplicate assignment names found";
            logger.error(error);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
         */

        return ResponseEntity.ok(missions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMissionById(@PathVariable(name = "id") String id) {
        Mission selectedMission = null;
        File dir = new File(missionDirectory);

        if (!dir.exists() || !dir.isDirectory()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
        if (files == null) {
            logger.error("No missions files found");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "No missions found"));
        }

        for (File file : files) {
            try {
                Mission mission = objectMapper.readValue(file, Mission.class);
                if (mission.getId().equals(id)) {
                    selectedMission = mission;
                    break;
                }
            } catch (UnrecognizedPropertyException e) {
                String error = "Unrecognized field \"" + AssignmentService.extractUnrecognizedField(e.getMessage()) + "\" not marked as ignorable found in file " + file.getName();
                logger.error("{}: {}", error, e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
            } catch (MismatchedInputException e) {
                String error = "Missing required property \"" + AssignmentService.extractMissingRequiredField(e.getMessage()) + "\" in file " + file.getName();
                logger.error("{}: {}", error, e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
            } catch (IOException e) {
                String error = "Error reading mission file " + file.getName();
                logger.error("{}: {}", error, e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
            }
        }

        /*
        if (AssignmentService.hasDuplicateNames(missions)) {
            String error = "Duplicate assignment names found";
            logger.error(error);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

         */

        if (selectedMission == null) {
            String error = "Mission with id \"" + id + "\" not found";
            logger.error("Error: {}", error);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } else {
            return ResponseEntity.ok(selectedMission);
        }
    }
}
