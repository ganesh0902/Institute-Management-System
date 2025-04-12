package com.std.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CredentialDto {
	
	private String name;
	private String email;
	private String password;
	private String role;
	private int instituteId;
}