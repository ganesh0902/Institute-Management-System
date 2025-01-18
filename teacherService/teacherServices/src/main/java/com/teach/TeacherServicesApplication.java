package com.teach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@EnableCaching
public class TeacherServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeacherServicesApplication.class, args);
		
		System.out.println("Application is running from: " + System.getProperty("user.dir"));

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
