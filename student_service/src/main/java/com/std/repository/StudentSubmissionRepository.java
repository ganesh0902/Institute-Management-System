package com.std.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.std.entities.StudentSubmission;

import jakarta.transaction.Transactional;

@Repository
public interface StudentSubmissionRepository extends JpaRepository<StudentSubmission,Long>{

	@Query("select s from StudentSubmission s where s.stdId = :stdId")
	List<StudentSubmission> getSubmissionByStudentId(@Param("stdId") int stdId);
	
	@Query("select b from StudentSubmission b where b.batchId =:batchId")
	List<StudentSubmission> getStudentSubmissionByBatchId(@Param("batchId") int batchId);
	
	@Query("select s from StudentSubmission s where s.assignmentId = :assignmentId")
	List<StudentSubmission> getStudentByAssignmentId(@Param("assignmentId") int assignmentId);

	@Modifying
	@Transactional
	@Query("UPDATE StudentSubmission s SET s.status =:status WHERE s.submissionId = :submissionId and stdId = :stdId")
	void updateAssignmentStatus(@Param("status") String status, @Param("submissionId") int assignmentId, @Param("stdId") int stdId);
	
	Optional<StudentSubmission> findBySubmissionId(int submissionId);
	Optional<StudentSubmission> findByStdId(int stdId);
}