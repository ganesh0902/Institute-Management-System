package com.std.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.std.entities.StudentSubmission;
import com.std.exception.ResourceNotFoundException;
import com.std.repository.StudentSubmissionRepository;
import com.std.service.AssignmentService;

@Service
public class StudentSubmissionImpl implements AssignmentService {

	@Autowired
	private StudentSubmissionRepository submissionRepository;

	@Override
	public StudentSubmission saveSubmission(StudentSubmission studentSubmission) {
		
		if(studentSubmission.getSubmissionId()==null)
		{
			studentSubmission.setStatus("active");
			studentSubmission.setDate(new Date().toLocaleString());
		}
		StudentSubmission save = this.submissionRepository.save(studentSubmission);
		return save;
	}

	@Override
	public List<StudentSubmission> getStudentSubmissionByStudentId(int stdSubmId) {

		List<StudentSubmission> submissionByStudentId = this.submissionRepository.getSubmissionByStudentId(stdSubmId);
		return submissionByStudentId;
	}

	@Override
	public List<StudentSubmission> getSubmissionByBatchId(int batchId) {

		List<StudentSubmission> studentSubmissionByBatchId = this.submissionRepository
				.getStudentSubmissionByBatchId(batchId);
		return studentSubmissionByBatchId;
	}

	@Override
	public List<StudentSubmission> getAllSubmission() {
		List<StudentSubmission> findAll = this.submissionRepository.findAll();
		return findAll;
	}

	@Override
	public StudentSubmission findBySubmissionId(Long sbId) throws ResourceNotFoundException {
		
		StudentSubmission submission = this.submissionRepository.findById(sbId).orElseThrow(()-> new ResourceNotFoundException("Submission", "Id",String.valueOf(sbId)));
		return submission;
	}

	@Override
	public List<StudentSubmission> getStudentByAssignmentId(int assignmentId) {
		List<StudentSubmission> studentByAssignmentId = this.submissionRepository.getStudentByAssignmentId(assignmentId);
		return studentByAssignmentId;
	}

}
