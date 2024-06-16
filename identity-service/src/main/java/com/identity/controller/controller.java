package com.identity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.identity.dto.AuthRequest;
import com.identity.entity.UserCredential;
import com.identity.service.AuthService;

@RequestMapping("/auth")
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:9001"})
public class controller {

	@Autowired
	private AuthService authService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/register")
	public UserCredential saveUser(@RequestBody UserCredential user) {
		return this.authService.saveUser(user);
	}

	@PostMapping("/token")
	public String generateToken(@RequestBody AuthRequest user) {

		String response ="";
		try
		{
		System.out.println("User is " + user);
		Authentication authenticate = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		if (authenticate.isAuthenticated()) {
			response = this.authService.generateToken(user.getUsername());
		} else {
			response = "Invalid Authentication";
		}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return response;
	}

	@GetMapping("/validate")
	public String validateToken(@RequestParam("token") String token) {
		
		this.authService.validateToken(token);
		return "Token validated";
	}
}
