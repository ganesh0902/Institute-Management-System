package com.teach.config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.net.HttpHeaders;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    
	final List<String> PUBLIC_URLS = List.of("/teacher/teacherCount/**");

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        String path = request.getRequestURI();
        
        System.out.println("Path is in Teacher "+path);
        if (isPublicEndpoint(path)) {
			filterChain.doFilter(request, response);
			System.out.println("Request is in teacher");	
			return;
		}		
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
                    
            System.out.print("Request is authorized");
            try {
                String username = jwtService.extractUsername(token);
                List<String> roles = jwtService.extractRoles(token); // get from "roles" claim

                List<GrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                throw new RuntimeException("Invalid Token");
            }
        }

        filterChain.doFilter(request, response);
    }    
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

	private boolean isPublicEndpoint(String path) {
	    return PUBLIC_URLS.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
	}
}
