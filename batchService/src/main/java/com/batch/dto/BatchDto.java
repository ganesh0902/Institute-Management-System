package com.batch.dto;

import com.batch.entities.Course;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BatchDto {

	private int bId;
	private String batchTitle;
	private String duration;
	private String startDate;
	private String endDate;
	private String status;
	private String location;
	private String time;
	private String image;
	private Course course;
	private int teacherId;
	private int courseId;
	
	private TeacherDto teacherDto;
	
	public BatchDto(int bId, String batchTitle, String duration, String startDate, String endDate, String status,
			String location, String time, String image, Course course) {
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
		
		this.course = course;
	}


	public BatchDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public BatchDto(int bId, String batchTitle, String duration, String startDate, String endDate, String status,
			String location, String time, String image,  Course course,
			TeacherDto teacherDto) {
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
		this.course = course;
		this.teacherDto = teacherDto;
	}

}