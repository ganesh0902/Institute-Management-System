package com.course.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.course.entity.Course;

public interface CourseRepository extends JpaRepository<Course,Integer>{
	
	@Query("SELECT c FROM Course c WHERE c.cid = :cId")
	 List<Course> findBycourseId(@Param("cId") int cid);
		
}
 