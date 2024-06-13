package com.identity.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.identity.entity.UserCredential;
import com.identity.repository.UserCredentialRepository;

public class CustomeUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserCredentialRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		 Optional<UserCredential>  user = this.repo.findByName(username);
				 
		return user.map(CustomeUserDetails::new).orElseThrow(()-> new UsernameNotFoundException("User Not Found with name "+username));
	}

}
