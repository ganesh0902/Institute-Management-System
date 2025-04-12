package com.batch.config;

import java.util.List;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtService {
	
	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

	public String extractUsername(String token) {
	    return extractAllClaims(token).getSubject();
	}

	public List<String> extractRoles(String token) {
	    Claims claims = extractAllClaims(token);
	    return claims.get("roles", List.class);
	}

	private Claims extractAllClaims(String token) {
	    return Jwts.parser()
	            .setSigningKey(SECRET) //  secret key
	            .parseClaimsJws(token)
	            .getBody();
	}
}