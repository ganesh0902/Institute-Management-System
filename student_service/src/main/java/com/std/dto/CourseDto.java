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

	private String courseName;
	private String description;
	private String skills;
	private String fees;	
}
