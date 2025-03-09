package com.teach.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TeacherDto {

	private int tId;
	private String firstName;
	private String lastName;
	private String education;
	private String contact;
	private String email;
	private BatchDto batchDto;
		
	public TeacherDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacherDto(int tId, String firstName, String lastName, String education, String contact, String email,
			BatchDto batchDto) {
		super();
		this.tId = tId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.education = education;
		this.contact = contact;
		this.email = email;
		this.batchDto = batchDto;
	}
		
}
