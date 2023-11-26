package com.shasha.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shasha.blog.exception.EmailNotFoundException;
import com.shasha.blog.repository.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		return userRepo.findByEmail(email).orElseThrow(()->
		new EmailNotFoundException("Email not found::"+email));
	}
	 
}
