package com.std.entities;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class StudentSubmission {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long submissionId;
	private int assignmentId;	
	private String solution;
	private String date;
	private String status;
	private int stdId;
	private int batchId;
	
}