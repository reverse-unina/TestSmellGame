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

import com.dariotintore.tesi.exerciseservice.Service.FileService;

@RestController
@RequestMapping("/files")
public class FileController {

  @Autowired
  private FileService storageService;

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

}
