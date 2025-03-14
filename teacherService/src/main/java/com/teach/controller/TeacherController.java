package com.teach.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.teach.dto.BatchDto;
import com.teach.dto.CourseDto;
import com.teach.dto.TeacherDto;
import com.teach.entities.Teacher;
import com.teach.exception.ResourceNotFoundException;
import com.teach.serviceImpl.TeacherServiceImpl;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	private TeacherServiceImpl teacherServiceImpl;
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	@PostMapping("/")
	public ResponseEntity<Teacher> saveTeacher(@RequestBody Teacher teacher)
	{
		Teacher saveTeacher = this.teacherServiceImpl.saveTeacher(teacher);							
		return new ResponseEntity<Teacher>(saveTeacher,HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<TeacherDto> getTeacherById(@PathVariable("id") int id) throws ResourceNotFoundException
	{
		TeacherDto teacherDto = this.teacherServiceImpl.getTeacherById(id);							
		
		return new ResponseEntity<TeacherDto>(teacherDto,HttpStatus.OK); 		
	}
		
}