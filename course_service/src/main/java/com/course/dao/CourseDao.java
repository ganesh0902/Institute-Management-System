package com.course.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.course.dto.CourseIdAndName;
import com.course.entity.Course;
import com.course.exception.ResourceNotFoundException;

public interface CourseDao {

	public Course saveCourse(Course course);
	public List<Course> getAll(long instituteId);
	public Course findById(int cid) throws ResourceNotFoundException;
	public boolean delete(int cid) throws ResourceNotFoundException;
	public List<CourseIdAndName> getAllCourseIdAndName(long instituteId);
	public List<Course> getCourseByName(String courseName);		
	public long getCountOfCourseAvailable(long instituteId);	
}