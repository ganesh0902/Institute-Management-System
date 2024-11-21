package com.batch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.batch.dto.BatchTitleAndDate;
import com.batch.entities.Batch;

public interface BatchRepository extends JpaRepository<Batch,Integer>{

	 	@Query("SELECT b FROM Batch b WHERE b.teacherId = :teacherId")
	    List<Batch> findAllByTeacherId(int teacherId);
	 	
	 	@Query("SELECT b FROM Batch b WHERE b.teacherId = :teacherId")
	    Batch findByTeacherId(int teacherId);	
	 	
	 	List<Batch> findByBatchTitleContaining(String batchName);
	 	
	 	@Query("SELECT new com.batch.dto.BatchTitleAndDate(b.bId, b.batchTitle, b.startDate) FROM Batch b where b.instituteId = :instituteId")
	 	List<BatchTitleAndDate> getBatchTitleAndStartDate(@Param("instituteId") long instituteId);
	 	
	 	@Query("select count(b) from Batch b where b.instituteId = :instituteId")
	 	long countBatchAvailable(@Param("instituteId") long instituteId);
	 		 	
	 	@Query("select b from Batch b where b.instituteId = :instituteId")
	 	List<Batch> findAllBatchByInstituteId(@Param("instituteId") long instituteId);
	 	
	 	@Query("select b from Batch b where b.courseId = :courseId")
	 	List<Batch> findByCourseId(@Param("courseId") int courseId);
} 