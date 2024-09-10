package com.std.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.std.dto.SubmissionDto;
import com.std.entities.Student;
import com.std.entities.StudentSubmission;
import com.std.exception.ResourceNotFoundException;
import com.std.repository.StudentRepository;
import com.std.repository.StudentSubmissionRepository;
import com.std.service.AssignmentService;

@Service
public class StudentSubmissionImpl implements AssignmentService {

	@Autowired
	private StudentSubmissionRepository submissionRepository;
	
	@Autowired
	private StudentImpl studentService;

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
	public List<SubmissionDto> getStudentByAssignmentId(int assignmentId) throws ResourceNotFoundException {
		List<StudentSubmission> studentByAssignmentId = this.submissionRepository.getStudentByAssignmentId(assignmentId);
		
		List<SubmissionDto> submisionList = new ArrayList<SubmissionDto>();
		
		for(StudentSubmission sbt: studentByAssignmentId)
		{
			SubmissionDto submission = new SubmissionDto();			
			Student std = this.studentService.findById(sbt.getStdId());
			
			submission.setAssignmentId(sbt.getAssignmentId());
			submission.setBatchId(sbt.getBatchId());
			submission.setDate(sbt.getDate());
			submission.setSubmissionId(sbt.getSubmissionId());
			submission.setStdId(sbt.getStdId());
			
			submission.setFirstName(std.getFirstName());
			submission.setLastName(std.getLastName());
			submission.setImage(std.getImage());
			submission.setSolution(sbt.getSolution());
			submission.setStatus(sbt.getStatus());	
			
			submisionList.add(submission);
			
		}
		return submisionList;
	}

	@Override
	public void updateStatus(String status, int submissionId, int stdId) throws ResourceNotFoundException {
		
		StudentSubmission submision = this.submissionRepository.findBySubmissionId(submissionId).orElseThrow(()-> new ResourceNotFoundException("submissionId","Id", String.valueOf(submissionId)));
				
		StudentSubmission student = this.submissionRepository.findByStdId(stdId).orElseThrow(()-> new ResourceNotFoundException("Student","Id",String.valueOf(stdId)));
		
		if(submision!=null && student!=null)
		{
			this.submissionRepository.updateAssignmentStatus(status,submissionId, stdId);	
		}						
	}
}