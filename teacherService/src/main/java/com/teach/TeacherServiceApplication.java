package com.teach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.teach.exception.ResourceNotFoundException;
import com.teach.serviceImpl.TeacherServiceImpl;

@SpringBootApplication
public class TeacherServiceApplication {

	public static void main(String[] args) throws ResourceNotFoundException {
		ConfigurableApplicationContext run = SpringApplication.run(TeacherServiceApplication.class, args);
		
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}
	
}
