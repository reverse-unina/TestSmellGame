package com.dariotintore.tesi.exerciseservice.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

import com.dariotintore.tesi.exerciseservice.entity.learningcontent.LearningContent;
import com.dariotintore.tesi.exerciseservice.entity.assignment.Assignment;
import com.dariotintore.tesi.exerciseservice.entity.exercise.checksmell.CheckSmellExercise;
import com.dariotintore.tesi.exerciseservice.entity.levelconfig.ToolConfig;
import com.dariotintore.tesi.exerciseservice.entity.mission.Mission;
import com.dariotintore.tesi.exerciseservice.entity.exercise.refactoring.RefactoringExercise;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class FileService {
	private final String refactoringDB = "ExerciseDB/RefactoringGame/";
	private final ObjectMapper objectMapper = new ObjectMapper();

	private static final Logger logger = LoggerFactory.getLogger(FileService.class);

	public <T> Object getAllJsonFilesInDirectory(String directory, Class<T> className) {
		Object result = this.getAllJsonFilePaths(directory);

		if (result instanceof Map)
			return result;

		List<Path> jsonFilePaths = (List<Path>) result;
		List<T> jsonFiles = new ArrayList<>();


		for (Path path : jsonFilePaths) {
			result = this.deserializeJson(path, className);

			if (result instanceof Map)
				return result;

			jsonFiles.add((T) result);
		}

		// switch construct support only primitive type, enum and String
		if (className.getName().equals(Assignment.class.getName())) {
			if (!(jsonFiles.get(0) instanceof Assignment)) {
				String error = "The first item in the list is not of the expected type 'Assignment'. Please ensure that the data is correctly formatted.";
				logger.error(error);
				return Map.of(HttpStatus.INTERNAL_SERVER_ERROR, error);
			}

			if (JsonCheckService.assignmentsHaveDuplicateNames((List<Assignment>) jsonFiles)) {
				String error = "Duplicate assignment names found";
				logger.error(error);
				return Map.of(HttpStatus.INTERNAL_SERVER_ERROR, error);
			}
		} else if (className.getName().equals(Mission.class.getName())) {
			if (!(jsonFiles.get(0) instanceof Mission)) {
				String error = "The first item in the list is not of the expected type 'Mission'. Please ensure that the data is correctly formatted.";
				logger.error(error);
				return Map.of(HttpStatus.INTERNAL_SERVER_ERROR, error);
			}

			if (JsonCheckService.missionsHaveDuplicateIds((List<Mission>) jsonFiles)) {
				String error = "Duplicate missions identifiers found";
				logger.error(error);
				return Map.of(HttpStatus.INTERNAL_SERVER_ERROR, error);
			}
		} else if (className.getName().equals(RefactoringExercise.class.getName())) {
			if (!(jsonFiles.get(0) instanceof RefactoringExercise)) {
				String error = "The first item in the list is not of the expected type 'RefactoringExercise'. Please ensure that the data is correctly formatted.";
				logger.error(error);
				return Map.of(HttpStatus.INTERNAL_SERVER_ERROR, error);
			}

			if (JsonCheckService.refactoringExerciseHaveDuplicateIds((List<RefactoringExercise>) jsonFiles)) {
				String error = "Duplicate exercise identifiers found";
				logger.error(error);
				return Map.of(HttpStatus.INTERNAL_SERVER_ERROR, error);
			}
		} else if (className.getName().equals(CheckSmellExercise.class.getName())) {
			if (!(jsonFiles.get(0) instanceof CheckSmellExercise)) {
				String error = "The first item in the list is not of the expected type 'CheckSmellExercise'. Please ensure that the data is correctly formatted.";
				logger.error(error);
				return Map.of(HttpStatus.INTERNAL_SERVER_ERROR, error);
			}

			if (JsonCheckService.checkSmellExerciseHaveDuplicateIds((List<CheckSmellExercise>) jsonFiles)) {
				String error = "Duplicate exercise identifiers found";
				logger.error(error);
				return Map.of(HttpStatus.INTERNAL_SERVER_ERROR, error);
			}
		} else if (className.getName().equals(ToolConfig.class.getName())) {
			if (!(jsonFiles.get(0) instanceof ToolConfig)) {
				String error = "The first item in the list is not of the expected type 'LevelConfig'. Please ensure that the data is correctly formatted.";
				logger.error(error);
				return Map.of(HttpStatus.INTERNAL_SERVER_ERROR, error);
			}
		}

		return jsonFiles;
	}

	public Object getRefactoringExerciseFile(String exerciseId, String type) {
		Object result = this.getAllJsonFilePaths(refactoringDB);

		if (result instanceof Map)
			return result;

		List<Path> configFilePaths = (List<Path>) result;
		Optional<Path> exercisePath = configFilePaths.stream()
				.filter(path -> ((RefactoringExercise) this.deserializeJson(path, RefactoringExercise.class)).getExerciseId().equals(exerciseId))
				.findFirst();

		if (exercisePath.isPresent()) {
			File parentDirectory;
			Optional<File> productionFile;
			switch (type) {
				case "Production":
					parentDirectory = exercisePath.get().getParent().toFile();
					logger.info("Parent directory: " + parentDirectory.getAbsolutePath());

					productionFile = Arrays.stream(Objects.requireNonNull(parentDirectory.listFiles()))
							.filter(file -> file.getName().contains(".java") && !file.getName().endsWith("Test.java"))
							.findFirst();

					if (productionFile.isPresent()) {
						try {
							return Files.readAllBytes(productionFile.get().toPath());
						} catch (IOException e) {
							String error = "Failed to read " + exerciseId + " Production file";
							logger.error("{}: {}", error, e.getMessage());
							return Map.of(HttpStatus.INTERNAL_SERVER_ERROR, error);
						}
					} else {
						String error = "File not found";
						logger.error(error);
						return Map.of(HttpStatus.NOT_FOUND, error);
					}

				case "Test":
					parentDirectory = exercisePath.get().getParent().toFile();

					productionFile = Arrays.stream(Objects.requireNonNull(parentDirectory.listFiles()))
							.filter(file -> file.getName().contains("Test.java"))
							.findFirst();

					if (productionFile.isPresent()) {
						try {
							return Files.readAllBytes(productionFile.get().toPath());
						} catch (IOException e) {
							String error = "Failed to read " + exerciseId + " Test file";
							logger.error("{}: {}", error, e.getMessage());
							return Map.of(HttpStatus.INTERNAL_SERVER_ERROR, error);
						}
					} else {
						String error = "File not found";
						logger.error(error);
						return Map.of(HttpStatus.NOT_FOUND, error);
					}

				default:
					return this.deserializeJson(exercisePath.get(), RefactoringExercise.class);
			}
		} else {
			String error = "File not found";
			logger.error(error);
			return Map.of(HttpStatus.NOT_FOUND, error);
		}
    }

	public <T> Object getJsonFileById(String fileId, String directory, Class<T> className) {
		Object result = this.getAllJsonFilePaths(directory);

		if (result instanceof Map)
			return result;


		List<Path> configFilePaths = (List<Path>) result;
		logger.info("Paths in {}: {}", directory, configFilePaths);
		Optional<T> jsonExercise = configFilePaths.stream()
				.map(path -> (T) this.deserializeJson(path, className))
				.filter(exercise -> {
					if (className.getName().equals(CheckSmellExercise.class.getName())) {
						return ((CheckSmellExercise) exercise).getExerciseId().equals(fileId);
					} else if (className.getName().equals(RefactoringExercise.class.getName())) {
						return ((RefactoringExercise) exercise).getExerciseId().equals(fileId);
					} else if (className.getName().equals(LearningContent.class.getName())) {
						return ((LearningContent) exercise).getLearningId().equals(fileId);
					} else if (className.getName().equals(Mission.class.getName())) {
						return ((Mission) exercise).getId().equals(fileId);
					} else if (className.getName().equals(Assignment.class.getName())) {
						return ((Assignment) exercise).getAssignmentId().equals(fileId);
					} else {
						return false;
					}
				})
				.findFirst();

		if (jsonExercise.isPresent()) {
			return jsonExercise;
		} else {
			String error = "File not found";
			logger.error(error);
			return Map.of(HttpStatus.NOT_FOUND, error);
		}
	}

	public Object getAssignmentFilePathById(String fileId, String directory) {
		Object result = this.getAllJsonFilePaths(directory);

		if (result instanceof Map)
			return result;


		List<Path> configFilePaths = (List<Path>) result;
		logger.info("Paths: " + configFilePaths);

		for (Path path : configFilePaths) {
			Object assignment = this.deserializeJson(path, Assignment.class);
			if (assignment instanceof Assignment) {
				if (((Assignment) assignment).getAssignmentId().equals(fileId)) {
					return path.toString();
				}
			}
		}

		String error = "File not found";
		logger.error(error);
		return Map.of(HttpStatus.NOT_FOUND, error);
	}

	private Object getAllJsonFilePaths(String directory) {
		List<Path> jsonFilePaths = new ArrayList<>();
		File dir = new File(directory);

		if (!dir.exists() || !dir.isDirectory()) {
			return Map.of(HttpStatus.NOT_FOUND, "Database not found");
		}

		try {
			Files.walkFileTree(Paths.get(directory), new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					if (file.toString().endsWith(".json")) {
						jsonFilePaths.add(file);
					}
					return FileVisitResult.CONTINUE;
				}
			});

		} catch (IOException e) {
			String error = "Error occurred while reading json files";
			logger.error("{}: {}", error, e.getMessage());
			return Map.of(HttpStatus.INTERNAL_SERVER_ERROR, error);
		}


		if (jsonFilePaths.isEmpty()) {
			String error = "Json files not found";
			logger.error(error);
			return Map.of(HttpStatus.NOT_FOUND, error);
		}

		return jsonFilePaths;
	}

	private <T> Object deserializeJson(Path path, Class<T> className) {
		try {
            return objectMapper.readValue(path.toFile(), className);
		} catch (UnrecognizedPropertyException e) {
			String error = "Unrecognized field \"" + JsonCheckService.extractUnrecognizedField(e.getMessage()) + "\" not marked as ignorable found in file " + path.getFileName();
			logger.error("{}: {}", error, e.getMessage());
			return Map.of(HttpStatus.INTERNAL_SERVER_ERROR, error);
		} catch (MismatchedInputException e) {
			String error = "Missing required property \"" + JsonCheckService.extractMissingRequiredField(e.getMessage()) + "\" in file " + path.getFileName();
			logger.error("{}: {}", error, e.getMessage());
			return Map.of(HttpStatus.INTERNAL_SERVER_ERROR, error);
		} catch (IOException e) {
			String error = "Error reading mission file " + path.getFileName();
			logger.error("{}: {}", error, e.getMessage());
			return Map.of(HttpStatus.INTERNAL_SERVER_ERROR, error);
		}
	}

	public byte[] getBadgeFile(String basePath, String filename) throws IOException {
		return Files.readAllBytes(Paths.get(basePath + filename + ".png"));
	}

}

