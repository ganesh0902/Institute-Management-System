package com.teach.dto;

import java.util.List;
import lombok.Getter;
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
	private List<BatchDto> batchDto;
	private String image;
		
	public TeacherDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacherDto(int tId, String firstName, String lastName, String education, String contact, String email,
			List<BatchDto> batchDto) {
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