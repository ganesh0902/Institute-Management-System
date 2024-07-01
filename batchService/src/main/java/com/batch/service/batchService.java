package com.batch.service;

import java.util.List;

import com.batch.dto.BatchDto;
import com.batch.dto.BatchTitleAndDate;
import com.batch.dto.SingleBatchDto;
import com.batch.entities.Batch;
import com.batch.exception.ResourceNotFoundException;

public interface batchService {

	public BatchDto getBatch(int bId) throws ResourceNotFoundException;	
	public Batch saveBatch(Batch batch);
	public Batch updateBatch(int bId,Batch batch) throws ResourceNotFoundException;
	public boolean delete(int bId) throws ResourceNotFoundException;
	public List<BatchDto> getAllBatch();
	public List<BatchDto> getAllBatchByTeacherId(int tId);
	public List<BatchDto> findByBatchTitleContaining(String batchTitle);	
	public List<BatchTitleAndDate> getBatchTitleAndDate();
	public Long countBatchAvailable(long instituteId);
	public Batch getSingleBatch(int studentId) throws ResourceNotFoundException;
	public List<Batch> getBatchesByTeacherId(int tId);
}