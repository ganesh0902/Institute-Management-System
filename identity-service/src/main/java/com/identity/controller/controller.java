package com.identity.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.identity.service.UserService;

@RequestMapping("/auth")
@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:9001" })
public class controller {

	@Autowired
	private AuthService authService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService service;

	@PostMapping("/register")
	public UserCredential saveUser(@RequestBody UserCredential user) {
		return this.authService.saveUser(user);
	}

	@PostMapping("/token")
	public Map<String, Object> generateToken(@RequestBody AuthRequest user) {

		String response = "";
		Map<String, Object> userInformation = null;
		try {
			System.out.println("User is " + user);
			Authentication authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

			if (authenticate.isAuthenticated()) {
				response = this.authService.generateToken(user.getUsername());

				userInformation = validateToken(response);
			} else {
				response = "Invalid Authentication";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return userInformation;
	}

	@GetMapping("/validate")
	public Map<String, Object> validateToken(@RequestParam("token") String token) {

		return this.authService.validateToken(token);
	}

	@GetMapping("/user")
	public ResponseEntity<UserCredential> getUserInformation(@RequestParam("email") String email) {
		UserCredential user = this.service.getUser(email);

		return new ResponseEntity<UserCredential>(user, HttpStatus.OK);
	}
}
