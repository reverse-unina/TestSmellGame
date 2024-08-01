package com.dariotintore.tesi.exerciseservice.Controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dariotintore.tesi.exerciseservice.LevelConfig.LevelConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


@RestController
@RequestMapping("/levelconfig")
public class LevelConfigController {
	
    private static final String FILE_PATH = "/usr/src/app/assets/assignments/levelconfig/levelConfig.json";
    private static final String BASE_IMAGE_PATH = "/usr/src/app/assets/assignments/levelconfig/";

	
    @GetMapping("/get-levelconfig")
    public ResponseEntity<LevelConfig> getLevelConfig() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LevelConfig levelConfig = objectMapper.readValue(new File(FILE_PATH), LevelConfig.class);
            return new ResponseEntity<>(levelConfig, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@GetMapping("/badge/{badgeName}")
    public ResponseEntity<Resource> getBadge(@PathVariable String badgeName) {
        try {
            File file = new File(BASE_IMAGE_PATH + badgeName);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
