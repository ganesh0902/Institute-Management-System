package com.batch.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "batch")
public class Batch {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int bId;
	private String batchTitle;
	private String duration;
	private String startDate;
	private String endDate;
	private String status;
	private String location;
	private String time;
	private String image;
	private int teacherId;
	private int courseId;
	private Long instituteId;

	public Batch() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Batch(int bId, String batchTitle, String duration, String startDate, String endDate, String status,
			String location, String time, String image, int teacherId, int courseId, Long instituteId) {
		super();
		this.bId = bId;
		this.batchTitle = batchTitle;
		this.duration = duration;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.location = location;
		this.time = time;
		this.image = image;
		this.teacherId = teacherId;
		this.courseId = courseId;
		this.instituteId = instituteId;
	}

}