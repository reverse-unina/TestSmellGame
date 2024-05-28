package com.dariotintore.tesi.exerciseservice.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
public class FileService {
public List<String> getAllFiles() {
    return Stream.of(new File("ExerciseDB/").listFiles())
    .filter(file -> file.isDirectory())
    .map(File::getName)
    .collect(Collectors.toList());
}

public byte[] getFile(String exercise, String type) throws IOException {
    byte [] response = null;
    if(type.equals("Production")){
        response = Files.readAllBytes(Paths.get("ExerciseDB/" + exercise +  "/"+ exercise + ".java"));
    }
    else if(type.equals("Test")){
        response = Files.readAllBytes(Paths.get("ExerciseDB/" + exercise +  "/"+ exercise + "Test.java"));
    }
    else if(type.equals("Configuration")){
        response = Files.readAllBytes(Paths.get("ExerciseDB/" + exercise + "/" + exercise + "Config.json"));
    }
    return response;
}

}
