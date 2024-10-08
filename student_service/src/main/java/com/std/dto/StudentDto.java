package com.std.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

	private int stdId;
	private String firstName;
	private String lastName;
	private String passoutYear;	
	private String lastEducation;
	private String courseName;	
	private String image;	
	private TeacherDto teacherDto;
	private BatchDto batchDto;
}