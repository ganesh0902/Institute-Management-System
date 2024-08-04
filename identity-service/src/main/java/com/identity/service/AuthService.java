package com.identity.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.identity.entity.UserCredential;
import com.identity.repository.UserCredentialRepository;

@Service
public class AuthService {

	@Autowired
	private UserCredentialRepository repository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtService jwtService;

	public String generateToken(String username) {
		return jwtService.generateToken(username);
	}

	public Map<String, Object> validateToken(String username) {
		return jwtService.validateToken(username);
	}

	public String saveUser(UserCredential credential) {
		String response = "";

		Optional<UserCredential> findByEmail = this.repository.findByEmail(credential.getEmail());
		
		if(!findByEmail.isPresent())
		{
			credential.setPassword(encoder.encode(credential.getPassword()));
			UserCredential save = this.repository.save(credential);			
			response = String.valueOf(save.getId());
		}
		else
		{
			response ="User is Alredy exist";
		}
			
		return response;
	}
}