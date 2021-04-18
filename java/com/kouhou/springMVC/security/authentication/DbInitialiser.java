package com.kouhou.springMVC.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.kouhou.springMVC.entities.User;
import com.kouhou.springMVC.repositories.UserRepository;

@Service
public class DbInitialiser implements CommandLineRunner {

	private PasswordEncoder passwordEncoder;
	public DbInitialiser(PasswordEncoder passEncode) {
		this.passwordEncoder = passEncode;
	}
	@Autowired
	private UserRepository userRepository;
	@Override
	public void run(String... args) throws Exception {
		
		userRepository.save(new User(null,"admin",passwordEncoder.encode("1234"),true,"ADMIN,USER"));
		userRepository.save(new User(null,"user",passwordEncoder.encode("1234"),true,"USER"));
		
	}

}
