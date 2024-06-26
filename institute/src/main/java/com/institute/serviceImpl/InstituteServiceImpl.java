package com.institute.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.ResourceAccessException;

import com.institute.entities.Institute;
import com.institute.exception.ResourceNotFoundException;
import com.institute.repository.Repository;
import com.institute.service.InstituteServices;

public class InstituteServiceImpl implements InstituteServices {

	@Autowired
	private Repository repository;

	@Override
	public Institute save(Institute institute) {

		return this.repository.save(institute);
	}

	@Override
	public Institute getInstituteById(long id) throws ResourceNotFoundException {

		return this.repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Institute", "Id", String.valueOf(id)));
	}

	@Override
	public List<Institute> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Institute update(long id, Institute institute) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub

	}

}
