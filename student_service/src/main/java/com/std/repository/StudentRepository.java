package com.std.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.std.entities.Student;

public interface StudentRepository extends JpaRepository<Student,Integer>{

	List<Student> findByFirstNameContaining(String firstName);
	
	@Query("select count(*) from Student")
	Long countStudent();
}