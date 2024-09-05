package com.std.service;

import java.util.List;

import com.std.dto.SubmissionDto;
import com.std.entities.StudentSubmission;
import com.std.exception.ResourceNotFoundException;

public interface AssignmentService {

	 StudentSubmission saveSubmission(StudentSubmission studentSubmission);
	 StudentSubmission findBySubmissionId(Long sbId) throws ResourceNotFoundException;
	 List<StudentSubmission> getStudentSubmissionByStudentId(int stdSubmId);
	 List<StudentSubmission> getSubmissionByBatchId(int batchId);
	 List<StudentSubmission> getAllSubmission();
	 List<SubmissionDto> getStudentByAssignmentId(int assignmentId) throws ResourceNotFoundException;
}