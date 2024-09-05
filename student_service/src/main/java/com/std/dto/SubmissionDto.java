package com.std.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDto {

	private int stdId;
	private String firstName;
	private String lastName;	
	private String image;
	
	private Long submissionId;
	private int assignmentId;	
	private String solution;
	private String date;
	private String status;	
	private int batchId;
}
