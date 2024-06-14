package com.apigateway.filter;

import org.apache.hc.client5.http.entity.mime.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	@Autowired
	private RouteValidator validator;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public AuthenticationFilter() {
		super(Config.class);
	}

	public static class Config {

	}

	@Override
	public GatewayFilter apply(Config config) {

		return ((exchange,chain)->{
			
			if(validator.isSecure.test(exchange.getRequest()))
			{
				if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
				{
					throw new RuntimeException("Missing authorization header");
				}
				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				
				if(authHeader!=null && authHeader.startsWith("Beare"))
				{
					authHeader = authHeader.substring(7);
				}
				try
				{
					restTemplate.getForObject("http://IDENTITY-SERVICE/validate?token"+authHeader, String.class);
				}
				catch(Exception exception)
				{
					
				}
			}
			return chain.filter(exchange);
		});		
		
	}

}
