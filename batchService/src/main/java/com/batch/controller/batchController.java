package com.batch.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.batch.batchImpl.AssignmentImpl;
import com.batch.batchImpl.BatchServiceImpl;
import com.batch.dto.BatchDto;
import com.batch.dto.BatchTitleAndDate;
import com.batch.entities.Assignment;
import com.batch.entities.Batch;
import com.batch.exception.ApiResponse;
import com.batch.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/batch")
public class batchController {

	@Autowired
	private BatchServiceImpl serviceImpl;
	
	@Autowired
	private AssignmentImpl assignmentImpl;
	
	   @Value("${image.upload.dir}")
	   private String uploadDir;
	
	@GetMapping("/{batchId}")
	public BatchDto getbatch(@PathVariable("batchId") int batchId) throws ResourceNotFoundException
	{						
		BatchDto batch = serviceImpl.getBatch(batchId);			
		return batch;
	}
	
	@GetMapping("/single/{batchId}")
	public ResponseEntity<Batch> getSinglebatchByStudentId(@PathVariable("batchId") int batchId) throws ResourceNotFoundException
	{						
		Batch singleBatch = serviceImpl.getSingleBatch(batchId);		
		return new ResponseEntity<Batch>(singleBatch,HttpStatus.OK);
	}
	@GetMapping("/teacherId/{tId}")
	public ResponseEntity<List<Batch>> getbatchByTeacherId(@PathVariable("tId") int tid)
	{
		List<Batch> batchesByTeacherId = this.serviceImpl.getBatchesByTeacherId(tid);
		return new ResponseEntity<List<Batch>>(batchesByTeacherId,HttpStatus.OK);		
	}
	@GetMapping("/institute/{instituteId}")
	public ResponseEntity<List<BatchDto>> getAllBatch(@PathVariable("instituteId") long instituteId)
	{		
		List<BatchDto> allBatch = this.serviceImpl.getAllBatch(instituteId);		
		return new ResponseEntity<List<BatchDto>>(allBatch,HttpStatus.OK);		
	}
	@PostMapping("image")
	public ResponseEntity<String> saveImages(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException
	{
        String fileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(file.getOriginalFilename());		           		
        
        String filePath = System.getProperty("user.home") + File.separator +
                "Institute Management System UI" + File.separator +
                "institute" + File.separator +
                "public" + File.separator +
                "batch" + File.separator + fileName;
        
        file.transferTo(new File(filePath));
        
		return new ResponseEntity<String>(fileName,HttpStatus.OK);		
	}	
	@GetMapping("/teacher/{tId}")
	public ResponseEntity<List<BatchDto>> getListOfBatch(@PathVariable("tId") int bId)
	{		
		List<BatchDto> allBatchByTeacherId = this.serviceImpl.getAllBatchByTeacherId(bId);	
		System.out.println(allBatchByTeacherId);
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
	@GetMapping("/batchTitle/{batchTitle}")
	public ResponseEntity<List<BatchDto>> findByBatchTitle(@PathVariable("batchTitle") String batchTitle){
		
		List<BatchDto> findByBatchTitleContaining = this.serviceImpl.findByBatchTitleContaining(batchTitle);
						
		return new ResponseEntity<List<BatchDto>>(findByBatchTitleContaining,HttpStatus.OK);				
	}
	@GetMapping("/batchTitleAndDate/{instituteId}")
	public ResponseEntity<List<BatchTitleAndDate>> getBatchTitleAndDate(@PathVariable("instituteId") long instituteId)
	{
		List<BatchTitleAndDate> batchTitleAndDate = this.serviceImpl.getBatchTitleAndDate(instituteId);
		
		return new ResponseEntity<List<BatchTitleAndDate>>(batchTitleAndDate,HttpStatus.OK);
	}
	@GetMapping("/countBatch/{instituteId}")
	public ResponseEntity<Long> countBatchAvailable(@PathVariable("instituteId") long instituteId)
	{
		Long countBatchAvailable = this.serviceImpl.countBatchAvailable(instituteId);
		return new ResponseEntity<Long>(countBatchAvailable,HttpStatus.OK); 		
	}	
	@PostMapping("/assignmentSave")
	public ResponseEntity<Assignment> saveAssignment(@RequestBody Assignment assignment)
	{
		Assignment saveAssignment = this.assignmentImpl.saveAssignment(assignment);
		
		return new ResponseEntity<Assignment>(saveAssignment, HttpStatus.OK);
	}
	@GetMapping("/assignmentByBatchId/{batchId}")
	public ResponseEntity<List<Assignment>> getAllAssignmentByBatch(@PathVariable("batchId")  int batchId)
	{
		List<Assignment> allAssignmentByBatch = this.assignmentImpl.getAllAssignmentByBatch(batchId);
		
		return new ResponseEntity<List<Assignment>>(allAssignmentByBatch, HttpStatus.OK);
	}
	@GetMapping("/asssignmentByTeacherId/{teacherId}")
	public ResponseEntity<List<Assignment>> getAllTeacherById(@PathVariable("teacherId") int teacherId)
	{
		List<Assignment> allAssignmentByTeacherId = this.assignmentImpl.getAllAssignmentByTeacherId(teacherId);
		
		return new ResponseEntity<List<Assignment>>(allAssignmentByTeacherId,HttpStatus.OK);
	}
	@GetMapping("/course/{courseId}")
	public ResponseEntity<List<Batch>> findBatchByCourseId(@PathVariable("courseId") int courseId)
	{
		System.out.println("Request In Course");
		System.out.println(courseId);
		List<Batch> findByCourseId = this.serviceImpl.findByCourseId(courseId);
		System.out.println(findByCourseId);
		
		return new ResponseEntity<List<Batch>>(findByCourseId,HttpStatus.OK);
		
	}
	@GetMapping("/batchByTeacherId/{tId}")
	public ResponseEntity<List<Batch>> getBatchByTeacherId(@PathVariable("tId") int tId)
	{
		List<Batch> batchByTeacherId = this.serviceImpl.getBatchByTeacherId(tId);
		return new ResponseEntity<List<Batch>>(batchByTeacherId, HttpStatus.OK);
	}
}