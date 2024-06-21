package com.institute.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.institute.entities.Institute;

public interface Repository extends JpaRepository<Institute,Long>{

}
