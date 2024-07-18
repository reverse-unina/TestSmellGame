package com.dariotintore.tesi.exerciseservice.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @GetMapping("/")
  public ResponseEntity<List<String>> getListFiles() {
    return ResponseEntity.status(HttpStatus.OK).body(storageService.getAllFiles());
  }

  @GetMapping("/{exercise}/{type}")
  public ResponseEntity<byte[]> retrieveFile(
            @PathVariable String exercise,
            @PathVariable String type) throws IOException {
    byte[] file = storageService.getFile(exercise, type);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + exercise + "\"")
        .body(file);
  }
  
  @GetMapping("/configurations")
  public ResponseEntity<List<byte[]>> getAllConfigFiles() throws IOException {
    List<byte[]> configFiles = storageService.getAllConfigFiles();
    return ResponseEntity.status(HttpStatus.OK).body(configFiles);
  }
}

