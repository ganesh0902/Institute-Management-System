package com.identity.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.identity.entity.UserCredential;
import com.identity.exception.ResourceNotFoundException;
import com.identity.repository.UserCredentialRepository;

@Service
public class UserService {

	@Autowired
	private UserCredentialRepository repo;

	public UserCredential getUser(String email) throws ResourceNotFoundException {
		return this.repo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "credential", email));
	}
}