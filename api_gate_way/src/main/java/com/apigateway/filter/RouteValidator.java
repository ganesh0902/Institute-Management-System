package com.apigateway.filter;

import java.util.*;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {

	public static final List<String> openApiEndPoints = List.of(

			"/auth/register", 
			"/auth/token", 
			"/aureka", 
			"/api-docs");
	
	
	public Predicate<ServerHttpRequest> isSecure = request -> openApiEndPoints.stream().noneMatch(uri->request.getURI().getPath().contains(uri));
}
	