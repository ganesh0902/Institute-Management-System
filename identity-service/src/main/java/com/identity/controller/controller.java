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
import com.identity.dto.TeacherDto;
import com.identity.entity.UserCredential;
import com.identity.exception.ResourceNotFoundException;
import com.identity.service.AuthService;
import com.identity.service.UserService;
import com.identity.serviceImpl.ServiceDaoImpl;

import jakarta.ws.rs.GET;

@RequestMapping("/auth")
@RestController																							
@CrossOrigin(origins = { "http://localhost:3000"})
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

			System.out.println("Is Authenticate"+authenticate);
			if (authenticate.isAuthenticated()) {
				UserCredential userInfo = serviceDaoImpl.getUserInfo(user.getUsername());
				response = this.authService.generateToken(user.getUsername(),userInfo);				
				
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
			
			//authenticationManager is responsible to check user is authenticate or not 
			//authenticationManager manager take UsernamePasswordAuthenticationToken which contain username and password
			
			Authentication authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

			//if user is authenticate then generate the token
			if (authenticate.isAuthenticated()) {
				UserCredential userInfo = serviceDaoImpl.getUserInfo(user.getUsername());
				response = this.authService.generateToken(user.getUsername(),userInfo);

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
	public ResponseEntity<List<TeacherDto>> getAllTeacher(@PathVariable("instituteId") int instituteId)
	{		
		List<TeacherDto> allTeacher = this.serviceDaoImpl.getAllTeacher(instituteId);	
		
		System.out.println(allTeacher);
		return new ResponseEntity<List<TeacherDto>>(allTeacher, HttpStatus.OK);
	}
	@GetMapping("/")
	public String get()
	{
		return "Hello Java";
	}
}