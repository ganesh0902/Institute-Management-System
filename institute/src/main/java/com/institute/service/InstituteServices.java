package com.institute.service;

import java.util.List;

import com.institute.entities.Institute;
import com.institute.exception.ResourceNotFoundException;

public interface InstituteServices {

	public Institute save(Institute institute);
	public Institute getInstituteById(long id) throws ResourceNotFoundException;
	public List<Institute> getAll();
	public Institute update(long id, Institute institute);
	public void delete(long id);	
}