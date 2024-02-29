package com.std.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.std.repository.*;
import com.std.entities.Student;
import com.std.service.Service;
import com.std.exception.*;
@org.springframework.stereotype.Service
public class StudentImpl implements Service{

	@Autowired
	private StudentRepository repository;
	
	@Override
	public Student saveStudent(Student student) {
		
		return this.repository.save(student);							
	}

	@Override
	public Student updateStudent(int stdId, Student std) throws ResourceNotFoundException {

		Student student = this.repository.findById(stdId).orElseThrow(()-> new ResourceNotFoundException("Student","Id",String.valueOf(stdId)));
		
		student.setBatchId(std.getBatchId());
		student.setCourseName(std.getCourseName());
		student.setFirstName(std.getFirstName());
		student.setLastName(std.getLastName());
		student.setLastEducation(std.getLastEducation());
		student.setPassoutYear(std.getPassoutYear());		
		this.repository.save(student);
		
		return null;
	}

	@Override
	public Student getStudent(int stdId) throws ResourceNotFoundException {
		
		Student student = this.repository.findById(stdId).orElseThrow(()-> new ResourceNotFoundException("Student","Id",String.valueOf(stdId)));				
		return student;
	}

	@Override
	public boolean delete(int stdId) throws ResourceNotFoundException {
		
		boolean status=false;
		Student student = this.repository.findById(stdId).orElseThrow(()-> new ResourceNotFoundException("Student","Id",String.valueOf(stdId)));
		
		if(student!=null)
		{
			status=true;			
			this.repository.deleteById(stdId);
		}		
		return status;
	}

	@Override
	public List<Student> getAll() {

		return this.repository.findAll();
				
	}
}
