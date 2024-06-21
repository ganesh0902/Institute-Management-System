package com.institute.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.institute.entities.Institute;

@RestController
@RequestMapping("/institute")
public class controller {

	@PostMapping("/")
	public String save(@RequestBody Institute institute)
	{
		return "";
	}
		
}