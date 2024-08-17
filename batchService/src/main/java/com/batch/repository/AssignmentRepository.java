package com.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.batch.entities.Assignment;
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer>{

}