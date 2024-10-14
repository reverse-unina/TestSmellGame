package com.dariotintore.tesi.exerciseservice.Assignment;

import java.util.List;

public class Assignment {
    private String name;
    private String date;
    private List<Student> students;
    private String type;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
