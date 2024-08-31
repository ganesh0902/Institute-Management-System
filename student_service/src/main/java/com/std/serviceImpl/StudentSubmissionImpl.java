package com.std.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.std.entities.StudentSubmission;
import com.std.repository.StudentSubmissionRepository;
import com.std.service.AssignmentService;

@Service
public class StudentSubmissionImpl implements AssignmentService {

	@Autowired
	private StudentSubmissionRepository submissionRepository;

	@Override
	public StudentSubmission saveSubmission(StudentSubmission studentSubmission) {
		StudentSubmission save = this.submissionRepository.save(studentSubmission);
		return save;
	}

	@Override
	public List<StudentSubmission> getStudentSubmissionByStudentId(int stdSubmId) {

		List<StudentSubmission> submissionByStudentId = this.submissionRepository.getSubmissionByStudentId(stdSubmId);
		return submissionByStudentId;
	}

	@Override
	public List<StudentSubmission> getSubmissionByBatchId(int batchId, int instituteId) {

		List<StudentSubmission> studentSubmissionByBatchId = this.submissionRepository
				.getStudentSubmissionByBatchId(batchId);
		return studentSubmissionByBatchId;
	}

	@Override
	public List<StudentSubmission> getAllSubmission() {
		List<StudentSubmission> findAll = this.submissionRepository.findAll();
		return findAll;
	}

}
