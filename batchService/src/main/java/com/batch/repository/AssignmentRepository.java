package com.batch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.batch.entities.Assignment;
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer>{
	
	@Query("select a from Assignment a where a.batchId = :batchId")
	public List<Assignment> getAllAssignmentsByBatchid(@Param("batchId") int batchId);
	
	@Query("select a from Assignment a where a.teacherId = :teacherId")
	public List<Assignment> getAllAssignmentByTeacherId(@Param("teacherId") int teacherId);

}