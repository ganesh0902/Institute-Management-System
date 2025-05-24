package com.course.config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	final List<String> PUBLIC_URLS = List.of("/course/getCourseIdAndName/2", "/course/*", "/public/**");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String header = request.getHeader(HttpHeaders.AUTHORIZATION);

		String path = request.getRequestURI();

		System.out.println("Path is "+path);
		if (isPublicEndpoint(path)) {
			filterChain.doFilter(request, response);
			System.out.println("Request is in course ");	
			return;
		}		
		if (header != null && header.startsWith("Bearer ")) {
			String token = header.substring(7);

			try {
				String username = this.jwtService.extractUsername(token);

				List<String> roles = this.jwtService.extractRoles(token);

				List<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList());

				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
						authorities);

				SecurityContextHolder.getContext().setAuthentication(auth);

			} catch (Exception e) {
				System.out.println(e.getMessage());
				throw new RuntimeException("Invalid Token");
			}

			filterChain.doFilter(request, response);
		}
	}

	private final AntPathMatcher pathMatcher = new AntPathMatcher();

	private boolean isPublicEndpoint(String path) {
	    return PUBLIC_URLS.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
	}
}