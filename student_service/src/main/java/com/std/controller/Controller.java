package com.std.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.std.entities.Student;
import com.std.exception.ApiResponse;
import com.std.exception.ResourceNotFoundException;
import com.std.serviceImpl.StudentImpl;

@RestController
@RequestMapping("/student")
public class Controller {

	@Autowired
	private StudentImpl service;
	
	@PostMapping("/")
	public ResponseEntity<Student> saveStudent(@RequestBody Student student)
	{
		Student saveStudent = this.service.saveStudent(student);		
		return new ResponseEntity<Student>(saveStudent,HttpStatus.OK);
	}
	
	@PutMapping("/{stdId}")
	public ResponseEntity<Student> updateStudent(@PathVariable("stdId") int stdId,@RequestBody Student student) throws ResourceNotFoundException
	{
		Student updateStudent = this.service.updateStudent(stdId, student);				
		return new ResponseEntity<Student>(updateStudent,HttpStatus.OK);
	}
			
	@GetMapping("/{stdId}")
	public ResponseEntity<Student> getStudent(@PathVariable("stdId") int stdId) throws ResourceNotFoundException
	{
		Student student = this.service.findById(stdId);		
		return new ResponseEntity<Student>(student,HttpStatus.OK);	
	}
	@GetMapping("/")
	public ResponseEntity<List<Student>> updateStudent()
	{
		List<Student> all = this.service.getAll();		
		return new ResponseEntity<List<Student>>(all,HttpStatus.OK);
	}
	@DeleteMapping("/{stdId}")
	public ResponseEntity<ApiResponse> deleteById(@PathVariable("stdId") int stdId) throws ResourceNotFoundException
	{	
			boolean status = this.service.delete(stdId);			
			ApiResponse apiResponse = new ApiResponse("",status);			
			return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);						
	}	
}