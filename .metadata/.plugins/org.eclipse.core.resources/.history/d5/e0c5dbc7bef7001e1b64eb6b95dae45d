package com.course.dao;

import java.util.List;

import com.course.entity.Course;
import com.course.exception.ResourceNotFoundException;

public interface CourseDao {

	
	public Course saveCourse(Course course);
	public List<Course> getAll();
	public Course findById(int cid) throws ResourceNotFoundException;
	public boolean delete(int cid) throws ResourceNotFoundException;
}
