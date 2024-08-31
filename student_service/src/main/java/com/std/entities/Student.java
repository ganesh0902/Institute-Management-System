package com.std.entities;

import java.util.List;


import org.hibernate.annotations.Cascade;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="student")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int stdId;
	private String firstName;
	private String lastName;
	private String passoutYear;
	private int batchId;
	private String lastEducation;
	private String courseName;	
	private String image;
	private Long instituteId;
		 
}
