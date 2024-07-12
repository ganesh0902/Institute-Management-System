package com.institute.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		
		info = @Info(
				title = "Institute API",
				description = "This service is for institute",
				summary = "This service is for to manage the all API end point ",
				termsOfService = "T&C",	
				contact = @Contact(
						name="Ganesh Sakhare",
						email = "ganeshs2987@gmail.com"
						),
				license = @License(
						
						name = "License number is 29299292"
						),
				version = "v1"
				),
		servers = @Server(  //here we can add multiple server for testing and live					
				description = "Dev",
				url = "http://localhost:8080"
				)				
		)


public class OpenApiConfig {

}
