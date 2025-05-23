package com.std.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.aspectj.weaver.ast.Instanceof;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.std.dto.StudentDto;
import com.std.entities.Student;
import com.std.exception.ApiResponse;
import com.std.exception.ResourceNotFoundException;
import com.std.exception.ServiceFailureException;
import com.std.serviceImpl.StudentImpl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.util.StringUtils;

@RestController
@RequestMapping("/student")
public class Controller {

	@Autowired
	private StudentImpl service;

	@PostMapping("/")
	@CircuitBreaker(name="saveStudentCircuitBreaker",fallbackMethod = "saveStudentFallback")
	public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
		Student saveStudent = this.service.saveStudent(student);

		return new ResponseEntity<Student>(saveStudent, HttpStatus.OK);
	}
	
	public ResponseEntity<Student> saveStudentFallback(Student student, Throwable throwable)
	{
		Student fallbackStudent = new Student();
		String errorMessage = "Unknown error occurred";
		
		if(throwable instanceof ServiceFailureException)
		{
			errorMessage = throwable.getMessage();
		}
        fallbackStudent.setFirstName("Fallback: Unable to save student due to service failure");
        fallbackStudent.setLastEducation("Fallback: " +errorMessage);
		return new ResponseEntity<Student>(fallbackStudent,HttpStatus.OK);
	}

	@PostMapping("/image")
	public ResponseEntity<String> saveImage(@RequestParam("file") MultipartFile file)
			throws IllegalStateException, IOException {
		String fileName = UUID.randomUUID().toString() + "-" + StringUtils.cleanPath(file.getOriginalFilename());

		String filePath = System.getProperty("user.home") + File.separator + "Institute Management System UI"
				+ File.separator + "institute" + File.separator + "public" + File.separator + "student" + File.separator
				+ fileName;
		try {
			file.transferTo(new File(filePath));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<String>(fileName, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<Student>> getAllStudent() {
		List<Student> allStudent = this.service.getAllStudent();

		return new ResponseEntity<List<Student>>(allStudent, HttpStatus.OK);
	}

	@PutMapping("/{stdId}")
	public ResponseEntity<Student> updateStudent(@PathVariable("stdId") int stdId, @RequestBody Student student)
			throws ResourceNotFoundException {
		Student updateStudent = this.service.updateStudent(stdId, student);
		return new ResponseEntity<Student>(updateStudent, HttpStatus.OK);
	}

	@GetMapping("/{stdId}")
	public ResponseEntity<Student> getStudent(@PathVariable("stdId") int stdId) throws ResourceNotFoundException {
		Student student = this.service.findById(stdId);
		return new ResponseEntity<Student>(student, HttpStatus.OK);
	}

	@GetMapping("/institute/{instituteId}")
	public ResponseEntity<List<Student>> updateStudent(@PathVariable("instituteId") long instituteId) {
		List<Student> all = this.service.getAll(instituteId);
		return new ResponseEntity<List<Student>>(all, HttpStatus.OK);
	}

	@DeleteMapping("/{stdId}")
	public ResponseEntity<ApiResponse> deleteById(@PathVariable("stdId") int stdId) throws ResourceNotFoundException {
		boolean status = this.service.delete(stdId);
		ApiResponse apiResponse = new ApiResponse("Record Deleted", status);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	@GetMapping("/filter/{username}")
	public ResponseEntity<List<Student>> getStudentByFilter(@PathVariable("username") String username) {
		List<Student> studentByFilter = this.service.getStudentByFilter(username);
		return new ResponseEntity<List<Student>>(studentByFilter, HttpStatus.OK);
	}

	@GetMapping("/studentCount/{instituteId}")
	public ResponseEntity<Long> countStudents(@PathVariable("instituteId") Long instituteId) {
		Long courseStudent = this.service.courseStudent(instituteId);		
		return new ResponseEntity<Long>(courseStudent, HttpStatus.OK);
	}

	@GetMapping("/studentDetails/{sdtId}")
	@CircuitBreaker(name="handleStudentCircuiteBreaker",fallbackMethod = "studentAllDetailsBreaker")
	public ResponseEntity<StudentDto> getStudentDetails(@PathVariable("sdtId") int stdId)
			throws ResourceNotFoundException {		
		StudentDto studentDetails =  this.service.getStudentDetails(stdId);		
		return new ResponseEntity<StudentDto>(studentDetails, HttpStatus.OK);
	}
	
	public ResponseEntity<StudentDto> studentAllDetailsBreaker(int stdId,Throwable throwable)
	{
		StudentDto studentDto = new StudentDto();			
		String errorMessage ="";
		if(throwable instanceof ServiceFailureException)
		{
			errorMessage = throwable.getMessage();
		}
		studentDto.setLastName("Fall Back Error: "+errorMessage);
		return new ResponseEntity<StudentDto>(studentDto,HttpStatus.OK);
	}

	@GetMapping("/studentByBatch/{batchId}")
	public ResponseEntity<List<Student>> getStudentByBatchId(@PathVariable("batchId") int batchId) {
		List<Student> allStudentByBatch = this.service.getAllStudentByBatch(batchId);

		return new ResponseEntity<List<Student>>(allStudentByBatch, HttpStatus.OK);
	}

	@GetMapping("/studentByTeacherId/{tId}")
	@CircuitBreaker(name = "teacherCircuitBreaker", fallbackMethod = "teacherServiceBreaker")
	public ResponseEntity<List<Student>> getStudentByTeacherId(@PathVariable("tId") int tId) {
		List<Student> students = this.service.getStudentByTeacherId(tId);

		return new ResponseEntity<List<Student>>(students, HttpStatus.OK);
	}

	public ResponseEntity<List<Student>> teacherServiceBreaker(int tId, Throwable throwable) {
		// Log or handle the error
		List<Student> students = new ArrayList<>();
		Student s1 = new Student();
		s1.setFirstName("Fallback: Teacher service is not available");
		students.add(s1);
		return new ResponseEntity<>(students, HttpStatus.OK);
	}
	@GetMapping("/callFunction")
	public ResponseEntity<Integer> getTotalMarks()
	{
		int totalStudent = this.service.getTotalStudent();
		
		return new ResponseEntity<>(totalStudent,HttpStatus.OK);
	}
}