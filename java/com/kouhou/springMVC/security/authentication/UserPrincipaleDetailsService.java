package com.kouhou.springMVC.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kouhou.springMVC.entities.User;
import com.kouhou.springMVC.repositories.UserRepository;

@Service
public class UserPrincipaleDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		UserPrincipal userPrincipal = new UserPrincipal(user);
		if(user == null) {
			throw new UsernameNotFoundException("A user with name "+username+" not found");
			
		}
		return userPrincipal;
	}
	

}
