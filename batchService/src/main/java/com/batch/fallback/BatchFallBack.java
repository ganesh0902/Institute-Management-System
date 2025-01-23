package com.batch.fallback;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.batch.dto.BatchDto;
import com.batch.entities.Batch;
import com.batchDaoFallBack.FallBackDao;

@Component
public class BatchFallBack implements FallBackDao {

	@Override
	public BatchDto getBatchFallBack(int bId) {
		BatchDto dto = new BatchDto();

		dto.setBatchTitle("Fall Back Method is call");
		dto.setDuration("");
		dto.setStartDate("");
		dto.setEndDate("");
		dto.setStatus("");
		dto.setLocation("");
		dto.setTime("");
		dto.setCourse(null);
		dto.setTeacherId(0);
		dto.setCourseId(0);
		dto.setImage("");

		return dto;
	}

	public List<BatchDto> getAllBatch(long instituteId) {
		List<BatchDto> batchList = new ArrayList<>();

		BatchDto dto = new BatchDto();

		dto.setBatchTitle("Fall Back Method is call");
		dto.setDuration("");
		dto.setStartDate("");
		dto.setEndDate("");
		dto.setStatus("");
		dto.setLocation("");
		dto.setTime("");
		dto.setCourse(null);
		dto.setTeacherId(0);
		dto.setCourseId(0);
		dto.setImage("");

		batchList.add(dto);
		return batchList;

	}

	@Override
	public List<BatchDto> getAllBatchByTeacherId(int teacherId) {
		
		List<BatchDto> batchList = new ArrayList<>();

		BatchDto dto = new BatchDto();

		dto.setBatchTitle("Fallback Batch Service is not available");		
		dto.setDuration("");
		dto.setStartDate("");
		dto.setEndDate("");
		dto.setStatus("");
		dto.setLocation("");
		dto.setTime("");
		dto.setCourse(null);
		dto.setTeacherId(0);
		dto.setCourseId(0);
		dto.setImage("");

		batchList.add(dto);
		return batchList;				
	}

	@Override
	public List<BatchDto> findByBatchTitleContaining(String batchTitle) {

		
		List<BatchDto> batchList = new ArrayList<>();

		BatchDto dto = new BatchDto();

		dto.setBatchTitle("Fallback Batch Service is not available");		
		dto.setDuration("");
		dto.setStartDate("");
		dto.setEndDate("");
		dto.setStatus("");
		dto.setLocation("");
		dto.setTime("");
		dto.setCourse(null);
		dto.setTeacherId(0);
		dto.setCourseId(0);
		dto.setImage("");

		batchList.add(dto);
		return batchList;				
	}
}
