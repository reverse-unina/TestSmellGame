package com.dariotintore.tesi.exerciseservice.entity.assignment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import java.util.List;

public class Assignment {
    private String assignmentId;
    private List<Student> students;
    private String type;
	private String gameType;

	@JsonCreator
	public Assignment(
			@JsonProperty(value = "assignmentId", required = true) @NonNull String assignmentId,
			@JsonProperty(value = "students", required = true) @NonNull List<Student> students,
			@JsonProperty(value = "type", required = true) @NonNull String type,
			@JsonProperty(value = "gameType", required = true) @NonNull String gameType) {
		this.assignmentId = assignmentId;
		this.students = students;
		this.type = type;
		this.gameType = gameType;
	}

	public String getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(String assignmentId) {
		this.assignmentId = assignmentId;
	}
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
}
