package com.teach.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teach.entities.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher,Integer>{

}
