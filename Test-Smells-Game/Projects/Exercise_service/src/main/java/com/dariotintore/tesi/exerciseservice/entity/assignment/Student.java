package com.dariotintore.tesi.exerciseservice.entity.assignment;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

public class Student {
	private String name;
    private String exerciseId;
	private String startDate;
	private String endDate;
    private String startTime;
    private String endTime;
    private boolean submitted;

	public Student(@JsonProperty(value = "name", required = true) @NonNull String name, @JsonProperty(value = "exerciseId", required = true) @NonNull String exerciseId, @JsonProperty(value = "startDate", required = true) @NonNull String startDate, @JsonProperty(value = "endDate", required = true) @NonNull String endDate, @JsonProperty(value = "startTime", required = true) @NonNull String startTime, @JsonProperty(value = "endTime", required = true) @NonNull String endTime, @JsonProperty(value = "submitted", required = true) boolean submitted) {
		this.name = name;
		this.exerciseId = exerciseId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.submitted = submitted;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExerciseId() {
		return exerciseId;
	}
	public void setExerciseId(String exerciseId) {
		this.exerciseId = exerciseId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public boolean isSubmitted() {
		return submitted;
	}
	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
