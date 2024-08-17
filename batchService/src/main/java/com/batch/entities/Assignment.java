package com.batch.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int taskId;		
	private String title;
	private String Description;
	private String startDate;
	private String endDate;
	private int batchId;
}