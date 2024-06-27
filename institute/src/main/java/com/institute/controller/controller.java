package com.institute.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.institute.entities.Institute;
import com.institute.exception.ApiResponse;
import com.institute.exception.ResourceNotFoundException;
import com.institute.service.InstituteServices;
import com.institute.serviceImpl.InstituteServiceImpl;

@RestController
@RequestMapping("/institute")
public class controller {

	@Autowired
	private InstituteServiceImpl service;

	@PostMapping("/")
	public ResponseEntity<String> save(@RequestBody Institute institute) {
		Institute save = this.service.save(institute);

		return new ResponseEntity<String>("Record Save Successfully", HttpStatus.OK);

	}

	@PutMapping("/")
	public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody Institute institute)
			throws ResourceNotFoundException {
		Institute update = this.service.update(id, institute);

		return new ResponseEntity<String>("Record Updated Successfully", HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Institute> getInstituteById(@PathVariable("id") Long id) throws ResourceNotFoundException {

		Institute institute = this.service.getInstituteById(id);

		return new ResponseEntity<Institute>(institute, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<Institute>> getAllInstitute() {

		List<Institute> all = this.service.getAll();

		return new ResponseEntity<List<Institute>>(all, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteById(@PathVariable("id") Long id) throws ResourceNotFoundException {
		this.service.delete(id);

		ApiResponse apiResponse = new ApiResponse("Record deleted Successfully", true);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
}