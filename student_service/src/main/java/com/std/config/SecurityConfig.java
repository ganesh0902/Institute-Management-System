package com.std.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.std.securityException.CustomAccessDeniedHandler;
import com.std.securityException.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
    private JwtAuthenticationFilter jwtFilter;

	@Bean
	public SecurityFilterChain filter(HttpSecurity http,
            CustomAuthenticationEntryPoint entryPoint,
            CustomAccessDeniedHandler accessDeniedHandler) throws Exception {

		 http.csrf().disable().authorizeHttpRequests().requestMatchers("/student/public").permitAll()
				.requestMatchers("/student/public/**").permitAll()
				.requestMatchers("/student/admin/**").hasRole("ADMIN")
				.requestMatchers("/student/**").hasAnyRole("STUDENT", "ADMIN","TEACHER")
				.anyRequest()
				.authenticated()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(entryPoint) //handle 401
				.accessDeniedHandler(accessDeniedHandler);//handle 403
		 		 		 		
		 http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();		 		 
	}	
}
