package com.teach.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentDto {

	private int stdId;
	private String firstName;
	private String lastName;
	private String passoutYear;
	private int batchId;
	private String lastEducation;
	private String courseName;	
	private String image;
	private Long instituteId;
	private Long totalFees;
	private Long paidFees;
	private Long remainFees;
}
