package com.std.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.std.entities.Student;

public interface StudentRepository extends JpaRepository<Student,Integer>{

}
