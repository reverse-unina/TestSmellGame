package com.dariotintore.tesi.exerciseservice.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
public class FileService { 

	public List<String> getAllFiles(String basePath) {
	    return Stream.of(new File(basePath).listFiles())
	    .filter(file -> file.isDirectory())
	    .map(File::getName)
	    .collect(Collectors.toList());
	}

	public byte[] getFile(String basePath, String exercise, String type) throws IOException {
	    byte [] response = null;
	    if(type.equals("Production")){
	        response = Files.readAllBytes(Paths.get(basePath + exercise +  "/"+ exercise + ".java"));
	    }
	    else if(type.equals("Test")){
	        response = Files.readAllBytes(Paths.get(basePath + exercise +  "/"+ exercise + "Test.java"));
	    }
	    else if(type.equals("Configuration")){
	        response = Files.readAllBytes(Paths.get(basePath + exercise + "/" + exercise + "Config.json"));
	    }
	    return response;
	}

	public byte[] getBadgeFile(String basePath, String filename) throws IOException {
		return Files.readAllBytes(Paths.get(basePath + filename + ".png"));
	}

	public List<byte[]> getAllConfigFiles(String basePath) throws IOException {
		List<byte[]> configFiles = new ArrayList<>();
		List<String> exercises = getAllFiles(basePath);

		for (String exercise : exercises) {
			byte[] configFile = Files.readAllBytes(Paths.get(basePath + exercise + "/" + exercise + "Config.json"));
			configFiles.add(configFile);
		}

		return configFiles;
    }
}
