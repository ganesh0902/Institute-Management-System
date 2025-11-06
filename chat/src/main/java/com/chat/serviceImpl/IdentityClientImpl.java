package com.chat.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.chat.dto.UserCredentialDTO;
import com.chat.service.IdentityClient;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class IdentityClientImpl implements IdentityClient{

	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public UserCredentialDTO getUserById(int userId) {
		String url = "/identity-service/auth/" + userId;
		
		return this.restTemplate.getForObject(url, UserCredentialDTO.class);
	}

	@Override
	public List<UserCredentialDTO> getAllUser() {
		
		//String url = "/identity-service/auth/userById";
		String url = "http://localhost:9096/auth/userById";

		List<UserCredentialDTO> users = this.restTemplate
				.exchange(url, HttpMethod.GET, getToken(), new ParameterizedTypeReference<List<UserCredentialDTO>>() {
				}).getBody();

		return users;
	}

    private HttpEntity<String> getToken() {
	    ServletRequestAttributes attributes = 
	        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

	    if (attributes != null) {
	        HttpServletRequest request = attributes.getRequest();
	        String authHeader = request.getHeader("Authorization");
	        
	        HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", authHeader);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			
	        return entity;
	    }
	    return null;
	}

}
