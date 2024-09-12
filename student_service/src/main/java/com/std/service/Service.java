package com.std.service;

import java.util.List;

import com.std.dto.StudentDto;
import com.std.entities.Student;
import com.std.exception.ResourceNotFoundException;

public interface Service {
	
	public Student saveStudent(Student std);
	public Student updateStudent(int stdId,Student std) throws ResourceNotFoundException;
	public Student findById(int stdId) throws ResourceNotFoundException;
	public List<Student> getAll(long instituteId);
	public boolean delete(int stdId) throws ResourceNotFoundException;
	public List<Student> getStudentByFilter(String studentName);
	public Long courseStudent(long instituteId);
	public StudentDto getStudentDetails(int stdId) throws ResourceNotFoundException;
	public List<Student> getAllStudentByBatch(int batchId);
	public List<Student> getAllStudent();
}