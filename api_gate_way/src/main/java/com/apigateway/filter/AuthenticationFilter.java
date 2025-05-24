package com.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.apigateway.util.JwtService;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	@Autowired
	private RouteValidator validator;

	@Autowired
	private JwtService jwtUtil;

	public AuthenticationFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			String path = exchange.getRequest().getURI().getPath();
						
			// Temporary bypass for debugging
			// If the request is to /course/getCourseIdAndName/2 or other open paths, no
			// need to check for token 	
			
			System.out.println("-----------------------------------"+path);
			if (path.equals("/course/**") || path.equals("/batch/institute/**") || path.equals("/teacher/902")) {					
				return chain.filter(exchange); // No token check for this endpoint
			}

			if (validator.isSecured.test(exchange.getRequest())) {
				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					throw new RuntimeException("missing authorization header");
				}

				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				if (authHeader != null && authHeader.startsWith("Bearer ")) {
					authHeader = authHeader.substring(7);
					System.out.println("++++++++++++ Token Validate Successfully +++++++++++++++++");
				}
				try {
					jwtUtil.validateToken(authHeader);
				} catch (Exception e) {
					System.out.println("Invalid access!");
					throw new RuntimeException("unauthorized access to application");
				}
			}
			return chain.filter(exchange);
		};
	}

	public static class Config {

	}
}