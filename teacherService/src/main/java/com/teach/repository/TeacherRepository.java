package com.teach.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.teach.entities.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher,Integer>{

	@Query("SELECT COUNT(t) FROM Teacher t WHERE t.instituteId = :instituteId")
	Long countTeacherByInstituteId(@Param("instituteId") Long instituteId);

	@Query("select t from Teacher t where t.instituteId = :instituteId")
	List<Teacher> findByInstituteId(long instituteId);

	
}