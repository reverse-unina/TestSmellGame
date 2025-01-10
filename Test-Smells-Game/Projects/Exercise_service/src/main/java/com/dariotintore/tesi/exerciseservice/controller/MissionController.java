package com.dariotintore.tesi.exerciseservice.controller;

import com.dariotintore.tesi.exerciseservice.service.FileService;
import com.dariotintore.tesi.exerciseservice.entity.mission.Mission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/missions")
public class MissionController {
    @Autowired
    private FileService fileService;
    private final String missionDB = "/usr/src/app/assets/missions/";
    private static final Logger logger = LoggerFactory.getLogger(MissionController.class);

    @GetMapping("/")
    public ResponseEntity<Object> getMissions() {
        Object result = fileService.getAllJsonFilesInDirectory(missionDB, Mission.class);

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

    @GetMapping("/{missionId}")
    public ResponseEntity<Object> getMissionById(@PathVariable(name = "missionId") String missionId) {
        Object result = fileService.getJsonFileById(missionId, missionDB, Mission.class);

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
