package com.dariotintore.tesi.exerciseservice.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.io.FileWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.dariotintore.tesi.exerciseservice.Service.FileService;

@RestController
@RequestMapping("/files")
public class FileController {

  @Autowired
  private FileService storageService;
  
  private static final String README_URL = "https://raw.githubusercontent.com/LZannini/Thesis/main/README.md";
  private static final String LOG_FILE_PATH = "/usr/src/app/assets/assignments/";

  @GetMapping("/readme")
  public ResponseEntity<String> getReadme() {
      RestTemplate restTemplate = new RestTemplate();
      try {
          String readmeContent = restTemplate.getForObject(README_URL, String.class);
          return ResponseEntity.ok(readmeContent);
      } catch (Exception e) {
          return ResponseEntity.status(500).body("Error fetching the README");
      }
  }

  @GetMapping("/refactoring-game")
  public ResponseEntity<List<String>> getListRefactoringGameFiles() {
    return ResponseEntity.status(HttpStatus.OK).body(storageService.getAllFiles("ExerciseDB/RefactoringGame/"));
  }

    @GetMapping("/check-game")
    public ResponseEntity<List<String>> getListCheckGameFiles() {
        return ResponseEntity.status(HttpStatus.OK).body(storageService.getAllFiles("ExerciseDB/CheckGame/"));
    }

  @GetMapping("/refactoring-game/{exercise}/{type}")
  public ResponseEntity<byte[]> retrieveRefactoringGameFile(
            @PathVariable String exercise,
            @PathVariable String type) throws IOException {
    byte[] file = storageService.getFile("ExerciseDB/RefactoringGame/", exercise, type);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + exercise + "\"")
        .body(file);
  }

    @GetMapping("/check-game/{exercise}/{type}")
    public ResponseEntity<byte[]> retrieveCheckGameFile(
            @PathVariable String exercise,
            @PathVariable String type) throws IOException {
        byte[] file = storageService.getFile("ExerciseDB/CheckGame/", exercise, type);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + exercise + "\"")
                .body(file);
    }

    @GetMapping("/refactoring-game-configurations")
    public ResponseEntity<List<byte[]>> getAllRefactoringGameConfigFiles() throws IOException {
        List<byte[]> configFiles = storageService.getAllConfigFiles("ExerciseDB/RefactoringGame/");
        return ResponseEntity.status(HttpStatus.OK).body(configFiles);
    }
  
  @GetMapping("/check-game-configurations")
  public ResponseEntity<List<byte[]>> getAllCheckGameConfigFiles() throws IOException {
    List<byte[]> configFiles = storageService.getAllConfigFiles("ExerciseDB/CheckGame/");
    return ResponseEntity.status(HttpStatus.OK).body(configFiles);
  }
  
  @PostMapping("/log-event")
  public ResponseEntity<String> logEvent(@RequestBody EventLog eventLog) {
      try {
          String logFileName = "events.log";
          String logFilePath = LOG_FILE_PATH + logFileName;

          Files.createDirectories(Paths.get(LOG_FILE_PATH));
          System.out.println("Directories created or already exist: " + LOG_FILE_PATH);

          LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Europe/Rome")); 
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
          String formattedDateTime = localDateTime.format(formatter);

          try (PrintWriter out = new PrintWriter(new FileWriter(logFilePath, true))) {
              String logEntry = String.format("%s %s %s",
                      formattedDateTime,
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

  public static class EventLog {
	  private String player;
	  private String eventDescription;
	  private String timestamp;
      
	  public String getPlayer() {
		  return player;
	  }
		
	  public void setPlayer(String player) {
		  this.player = player;
	  }
		
	  public String getEventDescription() {
		  return eventDescription;
	  }
		
	  public void setEventDescription(String eventDescription) {
		  this.eventDescription = eventDescription;
	  }
		
	  public String getTimestamp() {
		  return timestamp;
	  }
		
	  public void setTimestamp(String timestamp) {
		  this.timestamp = timestamp;
	  }
  }
}

