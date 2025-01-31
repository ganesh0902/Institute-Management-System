package com.teach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@EnableCaching
public class TeacherServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeacherServicesApplication.class, args);
		
		try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		    System.out.println("Driver loaded successfully!");
		} catch (ClassNotFoundException e) {
		    e.printStackTrace();
		}

	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}
	
	@Bean
	public ObjectMapper objectMapper() {
	    return new ObjectMapper();
	}

}
