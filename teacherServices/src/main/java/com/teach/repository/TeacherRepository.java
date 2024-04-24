package com.teach.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.teach.entities.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher,Integer>{
	
	@Query("select e.tId, e.firstName, e.lastName from Teacher e")
	List<Object[]> getTeacherIdAndName();
	
	@Query("select count(*) from Teacher")
	long TeacherCount();
}