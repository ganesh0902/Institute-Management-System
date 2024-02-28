package com.batch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SingleBatchDto {

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
}
