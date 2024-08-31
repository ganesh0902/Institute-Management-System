package com.std.service;

import java.util.List;

import com.std.entities.StudentSubmission;

public interface AssignmentService {

	 StudentSubmission saveSubmission(StudentSubmission studentSubmission);
	 List<StudentSubmission> getStudentSubmissionByStudentId(int stdSubmId);
	 List<StudentSubmission> getSubmissionByBatchId(int batchId, int instituteId);
	 List<StudentSubmission> getAllSubmission();
	
}