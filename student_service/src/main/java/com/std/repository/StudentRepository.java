package com.std.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.std.entities.Student;

public interface StudentRepository extends JpaRepository<Student,Integer>{

	List<Student> findByFirstNameContaining(String firstName);
	
	@Query("select count(s) from Student s where s.instituteId = :instituteId")
	Long countStudentByInstituteId(@Param("instituteId") Long instituteId);
	
	 @Query("select s from Student s where s.instituteId = :instituteId")
	    List<Student> findByInstituteId(@Param("instituteId") long instituteId);
	
	 @Query("select s from Student s where s.batchId = :batchId")
	 Optional<List<Student>> getAllStudentByBatchId(@Param("batchId") int batchId);
	 
	 @Query("select s from Student s where s.firstName like %:stdName%")
	 List<Student> searchStudentByName(@Param("stdName") String stdName);
	 
	 @Query("select s from from Student s where s.batchId = :batchId")
	 public List<Student> findByTeacherId(@Param("batchId") int batchId);
}