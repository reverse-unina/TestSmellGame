package com.dariotintore.tesi.exerciseservice.Assignment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import java.util.List;

public class Assignment {
    private String name;
    private List<Student> students;
    private String type;

	@JsonCreator
	public Assignment(@JsonProperty(value = "name", required = true) @NonNull String name, @JsonProperty(value = "students", required = true) @NonNull List<Student> students, @JsonProperty(value = "type", required = true) @NonNull String type) {
		this.name = name;
		this.students = students;
		this.type = type;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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

     
}
