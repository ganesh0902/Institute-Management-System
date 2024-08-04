package com.identity.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.identity.dto.AuthRequest;
import com.identity.entity.UserCredential;
import com.identity.exception.ResourceNotFoundException;
import com.identity.service.AuthService;
import com.identity.service.UserService;
import com.identity.serviceImpl.ServiceDaoImpl;

@RequestMapping("/auth")
@RestController																							
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:9001", "http://192.168.0.120:3000", "http://192.168.0.114:3000" })
//@CrossOrigin(origins = "*")
public class controller {

	@Autowired
	private AuthService authService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService service;
	
	@Autowired
	private ServiceDaoImpl serviceDaoImpl;

	@PostMapping("/register")
	public ResponseEntity<String> saveUser(@RequestBody UserCredential user) {
		
		System.out.println("==============================================================");
		System.out.println(user);
		 String saveUser = this.authService.saveUser(user);
		 
		 return new ResponseEntity<String>(saveUser,HttpStatus.OK);
	}

	@PostMapping("/token")
	public ResponseEntity<String> generateToken(@RequestBody AuthRequest user) {

		String response = "";
		
		try {
			System.out.println("User is " + user);
			Authentication authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

			if (authenticate.isAuthenticated()) {
				response = this.authService.generateToken(user.getUsername());				
				
			} else {
				response = "Invalid Authentication";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@GetMapping("/validate")
	public Map<String, Object> validateToken(@RequestParam("token") String token) {

		return this.authService.validateToken(token);
	}

	@GetMapping("/login")
	public Map<String, Object> login(@RequestBody AuthRequest user) {

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
	@GetMapping("/user")
	public ResponseEntity<UserCredential> user(@RequestParam("email") String email) throws ResourceNotFoundException {
		
		UserCredential user = this.service.getUser(email);		
		return new ResponseEntity<UserCredential>(user,HttpStatus.OK);				
	}	
	@GetMapping("/teacher/{instituteId}")
	public ResponseEntity<List<UserCredential>> getAllTeacher(@PathVariable("instituteId") int instituteId)
	{
		List<UserCredential> allTeacher = this.serviceDaoImpl.getAllTeacher(instituteId);
		return new ResponseEntity<List<UserCredential>>(allTeacher, HttpStatus.OK);
	}
}