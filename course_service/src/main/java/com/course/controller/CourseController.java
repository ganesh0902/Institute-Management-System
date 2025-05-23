package com.course.controller;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.course.daoImpl.CourseDaoImpl;
import com.course.dto.CourseIdAndName;
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
	@GetMapping("/institute/{instituteId}")
	public ResponseEntity<List<Course>> getAll(@PathVariable("instituteId") long instituteId)
	{
		System.out.println("Institute Id is "+instituteId);
		List<Course> allCourses = this.courseService.getAll(instituteId);
		
		return new ResponseEntity<List<Course>>(allCourses,HttpStatus.OK);
	}
	@PostMapping("/")
	public ResponseEntity<Course> saveCourse(@RequestBody Course course)
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
	@GetMapping("/getCourseIdAndName/{instituteId}")
	public ResponseEntity<List<CourseIdAndName>> getAllCourseIdAndName(@PathVariable("instituteId") long instituteId)
	{				
		 List<CourseIdAndName> allCourseIdAndName = this.courseService.getAllCourseIdAndName(instituteId);
		 		 
		return new ResponseEntity<List<CourseIdAndName>>(allCourseIdAndName,allCourseIdAndName.isEmpty() ? HttpStatus.NOT_FOUND :HttpStatus.OK);
	}
	@GetMapping("/getCourseByName/{courseName}")
	public ResponseEntity<List<Course>> getCourseByName(@PathVariable("courseName") String courseName)
	{
		List<Course> courseByName = this.courseService.getCourseByName(courseName);
		return new ResponseEntity<List<Course>>(courseByName,HttpStatus.OK);					
	}	
	@GetMapping("/courseCount/{instituteId}")
	public ResponseEntity<Long> getCountOfCourseAvailable(@PathVariable("instituteId") long instituteId) {
				
	    long countOfCourseAvailable = this.courseService.getCountOfCourseAvailable(instituteId);
	    return new ResponseEntity<Long>(countOfCourseAvailable, HttpStatus.OK);
	}
}