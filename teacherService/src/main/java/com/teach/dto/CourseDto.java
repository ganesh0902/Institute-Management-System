package com.teach.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CourseDto {

	private int cid;
	
	private String courseName;
		
	private String description;
		
	private String skills;
		
	private String fees;

	public CourseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CourseDto(int cid, String courseName, String description, String skills, String fees) {
		super();
		this.cid = cid;
		this.courseName = courseName;
		this.description = description;
		this.skills = skills;
		this.fees = fees;
	}
	
	
}
