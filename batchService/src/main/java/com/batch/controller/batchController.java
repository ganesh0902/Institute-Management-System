package com.batch.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.batch.batchImpl.BatchServiceImpl;
import com.batch.dto.BatchDto;
import com.batch.dto.TeacherDto;
import com.batch.entities.Batch;
import com.batch.entities.Course;
import com.batch.exception.ApiResponse;
import com.batch.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/batch")
public class batchController {

	@Autowired
	private BatchServiceImpl serviceImpl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/{batchId}")
	public BatchDto getbatch(@PathVariable("batchId") int batchId) throws ResourceNotFoundException
	{						
		BatchDto batch = serviceImpl.getBatch(batchId);			
		return batch;
	}
	@GetMapping("/")
	public ResponseEntity<List<BatchDto>> getAllBatch()
	{		
		List<BatchDto> allBatch = this.serviceImpl.getAllBatch();		
		return new ResponseEntity<List<BatchDto>>(allBatch,HttpStatus.OK);		
	}
	@GetMapping("/teacher/{tId}")
	public ResponseEntity<List<BatchDto>> getListOfBatch(@PathVariable("tId") int bId)
	{		
		List<BatchDto> allBatchByTeacherId = this.serviceImpl.getAllBatchByTeacherId(bId);		
		return new ResponseEntity<List<BatchDto>>(allBatchByTeacherId,HttpStatus.OK);
	}	
	@PostMapping("/")
	public ResponseEntity<Batch> saveBatch(@RequestBody Batch batch)
	{
		Batch saveBatch = serviceImpl.saveBatch(batch);		
		return new ResponseEntity<Batch>(saveBatch,HttpStatus.OK);
	}
	@PutMapping("/{bId}")
	public ResponseEntity<Batch> updateBatch(@PathVariable("bId") int bId, @RequestBody Batch batch ) throws ResourceNotFoundException
	{
		Batch updateBatch = this.serviceImpl.updateBatch(bId, batch);		
		return new ResponseEntity<Batch>(updateBatch,HttpStatus.OK);				
	}
	@DeleteMapping("/{bId}")
	public ResponseEntity<ApiResponse> deleteBatch(@PathVariable("bId") int bId) throws ResourceNotFoundException
	{
		boolean status = this.serviceImpl.delete(bId);		
		ApiResponse apiResponse = new ApiResponse("Record deleted Successfully",status);		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
	}	
}