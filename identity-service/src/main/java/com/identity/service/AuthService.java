package com.identity.service;

import java.util.Map;

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
	
	public String generateToken(String username)
	{
		return jwtService.generateToken(username);
	}
	
	public Map<String,Object> validateToken(String username)
	{
		return jwtService.validateToken(username);
	}

	public UserCredential saveUser(UserCredential credential)
	{		
		
		System.out.println("Password is "+credential.getPassword());
		credential.setPassword(encoder.encode(credential.getPassword()));
		
		return this.repository.save(credential);
	}			
}