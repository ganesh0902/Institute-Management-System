package com.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouteValidator {

	public static final List<String> openApiEndpoints = List.of("/auth/register", "/auth/token", "/eureka",
			"/course/getCourseIdAndName/2", "/batch/institute/**", "/teacher/**");
	
	public Predicate<ServerHttpRequest> isSecured = request -> {
        String path = request.getURI().getPath();

        boolean secured = openApiEndpoints.stream().noneMatch(uri -> {
            if (uri.endsWith("/**")) {
                String prefix = uri.substring(0, uri.length() - 3);
                return path.startsWith(prefix);
            } else {
                return path.equals(uri);
            }
        });

        System.out.println("Request path: " + path + ", isSecured: " + secured);
        return secured;
    };
}