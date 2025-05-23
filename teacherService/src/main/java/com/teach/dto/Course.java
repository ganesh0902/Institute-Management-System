package com.teach.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Course {

	private int cid;
	
	private String courseName;
		
	private String description;
		
	private String skills;
		
	private String fees;		
}
