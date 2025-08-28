package com.teach.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.teach.entities.Conversation;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long>{

	 @Query("SELECT c FROM Conversation c WHERE c.teacher.tId = :teacherId AND c.studentId = :studentId")
	    Optional<Conversation> findByTeacherIdAndStudentId(@Param("teacherId") int teacherId,
	                                                       @Param("studentId") int studentId);
}