package com.institute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class InstituteApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstituteApplication.class, args);
	}
}