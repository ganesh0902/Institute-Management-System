package com.course.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.course.securityException.CustomAccessDeniedHandler;
import com.course.securityException.CustomAuthenticationEntryPoint;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	 @Autowired
	 private JwtAuthenticationFilter jwtFilter;
		
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, CustomAuthenticationEntryPoint entryPoint, CustomAccessDeniedHandler accessDeniedHandler) throws Exception
	{
		http.csrf(csrf->csrf.disable())
		.authorizeHttpRequests(auth-> auth.requestMatchers("/course/**").permitAll()
				.requestMatchers("/course/admin/**").hasRole("ADMIN")
				.requestMatchers("/course/**").hasAnyRole("ADMIN","TEACHER","STUDENT")
				.anyRequest().authenticated()
				)
		.sessionManagement(session->session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			).exceptionHandling(ex-> ex.authenticationEntryPoint(entryPoint)
					.accessDeniedHandler(accessDeniedHandler))
		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}			 
}