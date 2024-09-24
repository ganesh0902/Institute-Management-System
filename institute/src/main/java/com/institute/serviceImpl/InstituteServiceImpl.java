package com.institute.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
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
	@CachePut(cacheNames = "institute", key = "#institute.id")
	public Institute save(Institute institute) {

		return this.repository.save(institute);
	}

	@Override
	@Cacheable(cacheNames = "institute", key = "#id")
	public Institute getInstituteById(long id) throws ResourceNotFoundException {

		System.out.println("Getting institute Id from DataBase");
		return this.repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Institute", "Id", String.valueOf(id)));
	}

	@Override
	@Cacheable(cacheNames = "#institute")
	public List<Institute> getAll() {
		
		System.out.println("Getting Institute from DataBase");
		return this.repository.findAll();
	}

	@Override
	@CachePut(cacheNames = "institute", key = "#id")
	public Institute update(long id, Institute institute) throws ResourceNotFoundException {

		System.out.println("Update Institute by id from dataBase");
		Institute data = this.repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Institute", "Id", String.valueOf(id)));

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
	@CacheEvict(cacheNames = "institute", key = "#id")
	public void delete(long id) throws ResourceNotFoundException {

		Institute data = this.repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Institute", "Id", String.valueOf(id)));
		this.repository.deleteById(data.getId());
	}

	@Override
	public Institute findByEmailAddress(String email) throws ResourceNotFoundException {

		return this.repository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Institute","Email",email));
		
	}
}
