package com.course.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				title = "Course APIs",
				description = "This is Course APIs",
				summary = "This is Course APIs for Insert, Update, delete",
				termsOfService = "T&C",
				contact = @Contact(
						name="Ganesh Sakhare",
						email = "ganeshs2987@gmail.com"
					),
				license = @License(
						name = "License Number is 483xx7722yy"
						),
				version = "v1"		
				),
		servers = {
				@Server(
						description = "Dev",
						url = "http://localhost:8080"
					),
				@Server(
						description = "Test",
						url = "http://localhost:1111"
					),
		}
)
public class OpenApiConfig {

}
