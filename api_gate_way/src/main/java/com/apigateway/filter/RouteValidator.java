package com.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouteValidator {

	public static final List<String> openApiEndpoints = List.of("/auth/register", "/auth/token", "/eureka",
			"/course/getCourseIdAndName/2");

	public Predicate<ServerHttpRequest> isSecured = request -> {
		String path = request.getURI().getPath();

		// Check for exact match or prefix match with the open endpoints
		boolean secured = openApiEndpoints.stream().noneMatch(
				uri -> path.equals(uri) || path.startsWith(uri.replace("**", "/course/getCourseIdAndName/**")));

		System.out.println("Request path: " + path + ", isSecured: " + secured);
		return secured;
	};
}