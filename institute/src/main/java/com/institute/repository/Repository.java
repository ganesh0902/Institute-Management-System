package com.institute.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.institute.entities.Institute;

public interface Repository extends JpaRepository<Institute,Long>{

	Optional<Institute> findByEmail(String email);
}
