package com.batch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class BatchTitleAndDate {

	private int bId;
	private String batchTitle;
	private String startDate;		
	
}