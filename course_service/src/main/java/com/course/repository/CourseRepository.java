package com.course.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.course.entity.Course;

public interface CourseRepository extends JpaRepository<Course,Integer>{
	
	@Query("SELECT c FROM Course c WHERE c.cid = :cId")
	 List<Course> findBycourseId(@Param("cId") int cid);
		
	@Query("SELECT c.cid, c.courseName FROM Course c where c.instituteId = :instituteId")
	List<Object[]> getCourseIdAndName(@Param("instituteId") long instituteId);

	List<Course> findByCourseNameContaining(String courseName);
	
	@Query("select count(c) from Course c where c.instituteId =:instituteId")
	long findCourseAvailable(@Param("instituteId") long instituteId);
	
	@Query("select c from Course c where c.instituteId = :instituteId")
	List<Course> getAll(@Param("instituteId") long instituteId);
}