package com.std.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class CourseDto {

	public CourseDto(String string, String string2) {
		// TODO Auto-generated constructor stub
	}
	private String courseName;
	private String description;
	private String skills;
	private String fees;	
}
