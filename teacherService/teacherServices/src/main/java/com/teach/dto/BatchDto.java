package com.teach.dto;
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
	private int teacherId;	
	private Course course;
	private int courseId;
	private int instituteId;
	
	
	public BatchDto(int bId, String batchTitle, String duration, String startDate, String endDate, String status,
			String location, String time, String image, int teacherId, Course course, int courseId, int instituteId) {
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
		this.course = course;
		this.instituteId = instituteId;
	}

	public BatchDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}