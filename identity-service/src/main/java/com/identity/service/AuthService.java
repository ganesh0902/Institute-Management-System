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

		UserCredential userCredential = this.repository.findByEmail(credential.getEmail()).get();

		if (userCredential == null) {
			credential.setPassword(encoder.encode(credential.getPassword()));
			this.repository.save(credential);
			response = "Record Save Successfully";
		} else {
			response = "User is already exist";
		}
		return response;
	}
}