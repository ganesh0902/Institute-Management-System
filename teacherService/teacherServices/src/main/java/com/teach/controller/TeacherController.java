package com.teach.controller;

import java.io.File;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import com.teach.dto.TeacherDto;
import com.teach.dto.TeacherIdAndName;
import com.teach.entities.Teacher;
import com.teach.exception.ResourceNotFoundException;
import com.teach.serviceimpl.TeacherServiceImpl;

import ch.qos.logback.classic.Logger;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/teacher")
//@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:9002" })
public class TeacherController {

	@Autowired
	private TeacherServiceImpl teacherServiceImpl;

	@PostMapping("/")
	public ResponseEntity<Teacher> saveTeacher(@RequestBody Teacher teacher) {
		Teacher saveTeacher = this.teacherServiceImpl.saveTeacher(teacher);
		return new ResponseEntity<Teacher>(saveTeacher, HttpStatus.OK);
	}

	@PutMapping("/{tId}")
	public ResponseEntity<Teacher> updateTeacher(@PathVariable("tId") int tId, @RequestBody Teacher teacher)
			throws ResourceNotFoundException {
		
		System.out.println("Teacher Id is "+tId+""+teacher);
		Teacher updateTeacher = this.teacherServiceImpl.updateTeacher(tId, teacher);

		return new ResponseEntity<Teacher>(updateTeacher, HttpStatus.OK);
	}

	@PostMapping("/image")
	public ResponseEntity<String> saveImages(@RequestParam("file") MultipartFile file)
			throws IllegalStateException, IOException {
		String fileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(file.getOriginalFilename());

		String filePath = System.getProperty("user.home") + File.separator + "Institute Management System UI"
				+ File.separator + "institute" + File.separator + "public" + File.separator + "teacher" + File.separator
				+ fileName;

		try {
			file.transferTo(new File(filePath));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return new ResponseEntity<String>(fileName, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@CircuitBreaker(name="batchCircuitBreaker", fallbackMethod = "betchForFallBack")
	public ResponseEntity<TeacherDto> getTeacherById(@PathVariable("id") int id) throws ResourceNotFoundException {
		TeacherDto teacherDto = this.teacherServiceImpl.getTeacherById(id);
		
		return new ResponseEntity<TeacherDto>(teacherDto, HttpStatus.OK);
	}

	// creating callback method for circuit breaker
	
	public ResponseEntity<TeacherDto> betchForFallBack(int id, Exception e)
	{
		System.out.println(e.getMessage());
		
		TeacherDto fallbackTeacher = new TeacherDto();
	    fallbackTeacher.setTId(id);
	    fallbackTeacher.setFirstName("Fallback Batch Service is not available ");
	    fallbackTeacher.setLastName("User");
	    fallbackTeacher.setEmail("fallback@example.com");
	    fallbackTeacher.setEducation("N/A");
	    fallbackTeacher.setContact("N/A");
	    fallbackTeacher.setImage("fallback.png");
	    fallbackTeacher.setBatchDto(Collections.emptyList());
		
		return new ResponseEntity<TeacherDto>(fallbackTeacher,HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	
	@GetMapping("/institute/{instituteId}")
	public ResponseEntity<List<TeacherDto>> getAll(@PathVariable("instituteId") long instituteId) {
		System.out.println("getAll Teachers");
		List<TeacherDto> all = this.teacherServiceImpl.getAll(instituteId);
		return new ResponseEntity<List<TeacherDto>>(all, HttpStatus.OK);
	}

	@GetMapping("/getTeachers/{instituteId}")
	public ResponseEntity<List<TeacherIdAndName>> getTeacherIdAndName(@PathVariable("instituteId") long instituteId) {
		List<TeacherIdAndName> teacherIdAndName = this.teacherServiceImpl.getTeacherIdAndName(instituteId);
		System.out.println(teacherIdAndName);
		return new ResponseEntity<List<TeacherIdAndName>>(teacherIdAndName,
				teacherIdAndName.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

	@GetMapping("/teacherCount/{instituteId}")
	public ResponseEntity<Long> getTeacherCount(@PathVariable("instituteId") Long instituteId) {
		long teacherCount = this.teacherServiceImpl.getTeacherCount(instituteId);
		return new ResponseEntity<Long>(teacherCount, HttpStatus.OK);
	}
	@GetMapping("/credential/{credentialId}")
	public ResponseEntity<Teacher> getTeacherByCredential(@PathVariable("credentialId") int credentialId)
	{
		Teacher teacherByCredential = this.teacherServiceImpl.getTeacherByCredential(credentialId);
		
		return new ResponseEntity<Teacher>(teacherByCredential,HttpStatus.OK);
	}
}
