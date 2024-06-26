package com.identity.service;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

	public Map<String, Object> validateToken(String token) {

		try {
			Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);

			Claims chain = claimsJws.getBody();
			String username = chain.getSubject();
			Date expiration = chain.getExpiration();

			HashMap<String, Object> userInfo = new HashMap<>();
			userInfo.put("username", username);
			userInfo.put("expiration", expiration);

			return userInfo;

		} catch (

		SignatureException e) {
			throw new RuntimeException("Invalid JWT signature");
		} catch (ExpiredJwtException e) {
			throw new RuntimeException("Token has expired");
		} catch (Exception e) {
			throw new RuntimeException("Token validation failed");
		}
	}

	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName);
	}

	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder().setClaims(claims) // claims header signature and payload
				.setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
