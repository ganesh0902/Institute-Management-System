package com.std.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.std.dto.SubmissionDto;
import com.std.entities.StudentSubmission;
import com.std.exception.ResourceNotFoundException;
import com.std.serviceImpl.StudentSubmissionImpl;

@RequestMapping("/submission")
@RestController
public class SubmissionController {

	@Autowired
	private StudentSubmissionImpl service;

	@PostMapping("/")
	public ResponseEntity<StudentSubmission> saveSubmission(@RequestBody StudentSubmission studentSubmission) {
		StudentSubmission saveSubmission = this.service.saveSubmission(studentSubmission);

		return new ResponseEntity<StudentSubmission>(saveSubmission, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<StudentSubmission>> getAll() {
		List<StudentSubmission> allSubmission = this.service.getAllSubmission();
		return new ResponseEntity<List<StudentSubmission>>(allSubmission, HttpStatus.OK);
	}

	@GetMapping("/{sbId}")
	public ResponseEntity<StudentSubmission> getSubmissionByItsId(@PathVariable("sbId") Long sbId)
			throws ResourceNotFoundException {
		StudentSubmission findBySubmissionId = this.service.findBySubmissionId(sbId);

		return new ResponseEntity<StudentSubmission>(findBySubmissionId, HttpStatus.OK);
	}

	@GetMapping("/batch/{batchId}")
	public ResponseEntity<List<StudentSubmission>> getSubmissionByBatchId(@PathVariable("batchId") int batchId) {
		List<StudentSubmission> submissionByBatchId = this.service.getSubmissionByBatchId(batchId);

		return new ResponseEntity<List<StudentSubmission>>(submissionByBatchId, HttpStatus.OK);
	}
	
	@GetMapping("/assignment/{assignmentId}")
	public ResponseEntity<List<SubmissionDto>> getStudentByAssignmentId(@PathVariable("assignmentId") int assignmentId) throws ResourceNotFoundException
	{
		List<SubmissionDto> studentByAssignmentId = this.service.getStudentByAssignmentId(assignmentId);
		
		return new ResponseEntity<List<SubmissionDto>>(studentByAssignmentId, HttpStatus.OK);
		
	}
}
