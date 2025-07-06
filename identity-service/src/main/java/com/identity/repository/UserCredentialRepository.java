 package com.identity.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.identity.entity.UserCredential;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential,Integer>{

	Optional<UserCredential> findByEmail(String username);
		
	@Query("SELECT new com.identity.entity.UserCredential(uc.id, uc.name, uc.email, uc.role, uc.instituteId) "
			+ "from UserCredential uc where uc.role = 'ROLE_TEACHER' and uc.instituteId =:instituteId")
	List<UserCredential> getAllTeacher(@RequestParam("instituteId") int instituteId);	
}