package com.wallet.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wallet.entities.User;
import com.wallet.security.JwtUserFactory;
import com.wallet.service.UserService;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService service;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = service.findByEmail(username);
		
		if (user.isPresent()) {
			return JwtUserFactory.create(user.get());
		}
		
		
		throw new UsernameNotFoundException("Email não encontrado");
	}

}
