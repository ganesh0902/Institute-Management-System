package com.batch.common;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class GetTokenFromHeader {

	private String getToken() {
	    ServletRequestAttributes attributes = 
	        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

	    if (attributes != null) {
	        HttpServletRequest request = attributes.getRequest();
	        String authHeader = request.getHeader("Authorization");
	        return authHeader; // e.g., "Bearer eyJhbGciOiJIUzI1NiIs..."
	    }
	    return null;
	}

}