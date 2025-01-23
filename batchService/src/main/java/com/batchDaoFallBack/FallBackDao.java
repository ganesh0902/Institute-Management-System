package com.batchDaoFallBack;

import java.util.List;

import com.batch.dto.BatchDto;

public interface FallBackDao {

	public BatchDto getBatchFallBack(int bId);
	public List<BatchDto> getAllBatch(long instituteId);
	public List<BatchDto> getAllBatchByTeacherId(int teacherId);
	public List<BatchDto> findByBatchTitleContaining(String batchTitle);	
	
}
