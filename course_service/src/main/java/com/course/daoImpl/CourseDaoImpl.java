package com.course.daoImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.dao.CourseDao;
import com.course.entity.Course;
import com.course.exception.ResourceNotFoundException;
import com.course.repository.CourseRepository;

@Service
public class CourseDaoImpl implements CourseDao{

	@Autowired
	private CourseRepository repository;
		
	@Override
	public Course saveCourse(Course course) {
		
		return this.repository.save(course);						
	}

	@Override
	public Course findById(int cid) throws ResourceNotFoundException {
		
		return this.repository.findById(cid).orElseThrow(() -> new ResourceNotFoundException("course","Id",String.valueOf(cid)));		
		
	}

	@Override
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
	public List<Course> getAll() {
		
		return this.repository.findAll();
				
	}
}