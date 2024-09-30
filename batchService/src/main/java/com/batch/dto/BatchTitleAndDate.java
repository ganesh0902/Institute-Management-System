package com.batch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BatchTitleAndDate {

	private int bId;
	private String batchTitle;
	private String startDate;
	public BatchTitleAndDate(int bId, String batchTitle, String startDate) {
		super();
		this.bId = bId;
		this.batchTitle = batchTitle;
		this.startDate = startDate;
	}
	public BatchTitleAndDate() {
		super();
		// TODO Auto-generated constructor stub
	}
			
}
