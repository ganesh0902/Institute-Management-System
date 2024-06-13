package com.identity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
public class controller {

	@Autowired
	private AuthService authService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/register")
	public UserCredential saveUser(@RequestBody UserCredential user) {
		return this.authService.saveUser(user);
	}

	@GetMapping("/token")
	public String generateToken(@RequestBody AuthRequest user) {

		System.out.println("User is " + user);
		Authentication authenticate = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		if (authenticate.isAuthenticated()) {
			return this.authService.generateToken(user.getUsername());
		} else {
			return "Invalid Authentication";
		}
	}

	@GetMapping("/validate")
	public String validateToken(@RequestParam("token") String token) {
		
		this.authService.validateToken(token);
		return "Token validated";
	}
}
