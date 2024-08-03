package com.identity.serviceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.identity.entity.UserCredential;
import com.identity.repository.UserCredentialRepository;
import com.identity.service.ServiceDao;

@Service
public class ServiceDaoImpl implements ServiceDao{

	@Autowired
	private UserCredentialRepository repository;
	
	@Override
	public List<UserCredential> getAllTeacher(int instituteId) {
		
		return this.repository.getAllTeacher(instituteId);		
	}	
}