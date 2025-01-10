package com.dariotintore.tesi.exerciseservice.controller;

import com.dariotintore.tesi.exerciseservice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dariotintore.tesi.exerciseservice.entity.levelconfig.LevelConfig;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/levelconfig")
public class LevelConfigController {
	@Autowired
    private FileService fileService;
    private static final String LEVEL_CONFIG_DB = "/usr/src/app/assets/levelconfig/";

    @GetMapping("/")
    public ResponseEntity<Object> getLevelConfig() {
        Object result = fileService.getAllJsonFilesInDirectory(LEVEL_CONFIG_DB, LevelConfig.class);

        if (result instanceof Map) {
            Map<HttpStatus, String> resultMap = (Map<HttpStatus, String>) result;

            Map.Entry<HttpStatus, String> entry = resultMap.entrySet().iterator().next();
            HttpStatus httpStatus = entry.getKey();
            String error = entry.getValue();

            return ResponseEntity.status(httpStatus).body(Map.of("message", error));
        } else {
            return ResponseEntity.ok(((List<LevelConfig>) result).get(0));
        }
    }
}
