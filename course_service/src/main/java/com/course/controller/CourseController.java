package com.course.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.daoImpl.CourseDaoImpl;
import com.course.entity.Course;
import com.course.exception.ApiResponse;
import com.course.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/course")
public class CourseController {

	@Autowired
	private CourseDaoImpl courseService;
	
	@GetMapping("/{courseId}")
	public Course getCourse(@PathVariable("courseId") int courseId) throws ResourceNotFoundException
	{				
		return  courseService.findById(courseId);		
	}
	@GetMapping("/")
	public ResponseEntity<List<Course>> getAll()
	{
		List<Course> allCourses = this.courseService.getAll();
		
		return new ResponseEntity<List<Course>>(allCourses,HttpStatus.OK);
	}
	@PostMapping("/")
	public ResponseEntity<Course> saveCourse(@Validated @RequestBody Course course)
	{
		Course saveCourse = courseService.saveCourse(course);		
		return new ResponseEntity<Course>(saveCourse,HttpStatus.OK);
	}
	
	@DeleteMapping("/{cId}")
	public ResponseEntity<ApiResponse> deleteCourse(@PathVariable("cId") int cId) throws ResourceNotFoundException
	{
		boolean delete = this.courseService.delete(cId);
		
		 ApiResponse apiResponse = new ApiResponse("Record deleted Successfully",true);
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
	}
}