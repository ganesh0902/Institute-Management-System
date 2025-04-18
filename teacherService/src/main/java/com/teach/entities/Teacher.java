package com.teach.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity
@Table(name="teacher")
public class Teacher {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int tId;
	private String firstName;
	private String lastName;
	private String education;
	private String contact;
	private String email;
	private String image;
	private Long instituteId;
	private int credentialId;
}