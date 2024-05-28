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
public class TeacherDto {
	private int tId;
	private String firstName;
	private String lastName;
	private String education;
	private String contact;
	private String email;
	private String image;	
}