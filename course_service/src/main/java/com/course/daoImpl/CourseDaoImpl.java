package com.course.daoImpl;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.course.dao.CourseDao;
import com.course.dto.CourseIdAndName;
import com.course.entity.Course;
import com.course.entity.CourseTopic;
import com.course.exception.ResourceNotFoundException;
import com.course.repository.CourseRepository;

@Service
public class CourseDaoImpl implements CourseDao{

	@Autowired
	private CourseRepository repository;
		
	@Override
	public Course saveCourse(Course course) {
		
		LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String current = currentDate.format(formatter);        
        course.setLastUpdatedDate(current);                  
        System.out.println(course);
        
        for(CourseTopic topic : course.getTopics())
        {        	
        	topic.setCourse(course);
        }
        repository.save(course);
        
        System.out.println(course);
        
		return this.repository.save(course);						
	}

	@Override
	@Cacheable(cacheNames = "course", key = "#cid")
	public Course findById(int cid) throws ResourceNotFoundException {
		System.out.println("Getting Institute from DataBase");
		return this.repository.findById(cid).orElseThrow(() -> new ResourceNotFoundException("course","Id",String.valueOf(cid)));				
	}

	@Override
	@CacheEvict(cacheNames = "course", key = "#cid")
	public boolean delete(int cid) throws ResourceNotFoundException {

		boolean status=true;
		Course course = this.repository.findById(cid).orElseThrow(() -> new ResourceNotFoundException("Course","Id",String.valueOf(cid)));
						
		if(course==null)
		{
			status=false;
		}
		
		return status;
		
	}

	@Override
	public List<Course> getAll(long instituteId) {
		
		return this.repository.getAll(instituteId);
				
	}

	@Override
	public List<CourseIdAndName> getAllCourseIdAndName(long instituteId) {
		
		List<Object[]> courseIdAndName = this.repository.getCourseIdAndName(instituteId);
				
		return courseIdAndName.stream().map(course->{
			
			CourseIdAndName c = new CourseIdAndName();
			Object courseId=course[0];
			Object courseName = course[1];
			
			int courseid=(int) courseId;
			String coursename=courseName.toString();
			
			c.setCourseId(courseid);
			c.setCourseName(coursename);
			
			return c;						
		}).collect(Collectors.toList());
				
	}

	@Override
	public List<Course> getCourseByName(String courseName) {
		
		List<Course> findByCourseName = this.repository.findByCourseNameContaining(courseName);
		 
		return findByCourseName ;		
	}
	
	@Override
	public long getCountOfCourseAvailable(long instituteId) {
		
		return this.repository.findCourseAvailable(instituteId);		  	
	}	
}