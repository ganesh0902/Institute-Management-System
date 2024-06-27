package com.institute.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.institute.entities.Institute;
import com.institute.exception.ResourceNotFoundException;
import com.institute.repository.Repository;
import com.institute.service.InstituteServices;

@Service
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

		return this.repository.findAll();
	}

	@Override
	public Institute update(long id, Institute institute) throws ResourceNotFoundException {
		
		Institute data= this.repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Institute","Id", String.valueOf(id)));
		
		data.setDepartment(institute.getDepartment());
		data.setDomainUsername(institute.getDomainUsername());
		data.setEmail(institute.getEmail());
		data.setInstituteName(institute.getInstituteName());
		data.setStartDate(institute.getStartDate());
		data.setStatus(institute.getStatus());
		data.setAddress(institute.getAddress());
		
		return this.repository.save(data);				
	}

	@Override
	public void delete(long id) throws ResourceNotFoundException {
		
		Institute data = this.repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Institute","Id",String.valueOf(id)));
		this.repository.deleteById(data.getId());
	}

}
