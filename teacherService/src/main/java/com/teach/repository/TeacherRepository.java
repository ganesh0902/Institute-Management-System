package com.teach.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.teach.entities.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher,Integer>{
	
	@Query("select e.tId, e.firstName, e.lastName from Teacher e where e.instituteId = :instituteId")
	List<Object[]> getTeacherIdAndName(@Param("instituteId") long instituteId);
	
	@Query("select count(t) from Teacher t where t.instituteId =:instituteId")
	long TeacherCount(@Param("instituteId") Long instituteId);
	
	@Query("select t from Teacher t where t.instituteId = :instituteId")
	List<Teacher> findAllByInstitute(@Param("instituteId") long instituteId);
	
	@Query("select t from Teacher t where t.credentialId = :credentialId")
	Teacher getTeacherByCredential(@Param("credentialId") int credentialId);	
}