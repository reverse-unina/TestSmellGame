package com.dariotintore.tesi.exerciseservice.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.dariotintore.tesi.exerciseservice.service.FileService;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService = new FileService();

    private static final String README_URL = "https://raw.githubusercontent.com/LZannini/Thesis/main/README.md";
    private static final String ASSIGNMENT_LOG_FILE_PATH = "/usr/src/app/assets/assignments/";
    private static final String GAME_LOG_FILE_PATH = "/usr/src/app/assets/logs/";


    @GetMapping("/badges/{filename}")
    public ResponseEntity<Object> getBadgeImageByFilename(@PathVariable("filename") String filename) {
        byte[] image;
        try {
            image = this.fileService.getBadgeFile("/usr/src/app/assets/badges/", filename);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body("Error fetching image");
        }
    }

    @GetMapping("/readme")
    public ResponseEntity<String> getReadme() {
      RestTemplate restTemplate = new RestTemplate();
      try {
          String readmeContent = restTemplate.getForObject(README_URL, String.class);
          return ResponseEntity.ok(readmeContent);
      } catch (Exception e) {
          return ResponseEntity.status(500).body("Error fetching README");
      }
    }

    @PostMapping("/logger")
    public ResponseEntity<String> logEvent(@RequestBody EventLog eventLog) {
        try {
            if (!Files.exists(Paths.get(ASSIGNMENT_LOG_FILE_PATH)))
                Files.createDirectories(Paths.get(ASSIGNMENT_LOG_FILE_PATH));
            if (!Files.exists(Paths.get(GAME_LOG_FILE_PATH)))
                Files.createDirectories(Paths.get(GAME_LOG_FILE_PATH));

            String logFileName = "events.log";
            String logFilePath = eventLog.gameMode.contains("Assignment") ? ASSIGNMENT_LOG_FILE_PATH : GAME_LOG_FILE_PATH;
            logFilePath += logFileName;

            LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Europe/Rome"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = localDateTime.format(formatter);

            try (PrintWriter out = new PrintWriter(new FileWriter(logFilePath, true))) {
              String logEntry = String.format("%s [%s]: \t%s %s",
                      formattedDateTime,
                      eventLog.getGameMode(),
                      eventLog.getPlayer(),
                      eventLog.getEventDescription());
              out.println(logEntry);
              System.out.println("Log entry written: " + logEntry);
              System.out.println("Log file path: " + logFilePath);
            }

            return ResponseEntity.ok("{\"message\": \"Event logged successfully\"}");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Error logging event\"}");
        }
    }

    @Getter
    @Setter
    public static class EventLog {
        private String gameMode;
        private String player;
        private String eventDescription;
        private String timestamp;
    }
}

