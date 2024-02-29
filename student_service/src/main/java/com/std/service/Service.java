package com.std.service;
import java.util.List;

import com.std.entities.*;
import com.std.exception.ResourceNotFoundException;
public interface Service {

	public Student saveStudent(Student student);
	public Student updateStudent(int stdId,Student std) throws ResourceNotFoundException;
	public Student getStudent(int stdId) throws ResourceNotFoundException;
	public boolean delete(int stdId) throws ResourceNotFoundException;
	public List<Student> getAll();	
}