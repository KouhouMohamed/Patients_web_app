package com.kouhou.springMVC.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	
	//How to search users
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder bCryptPasswordEncoder = (BCryptPasswordEncoder) passwordEncoder();
		//{noop} : no password encoder
		auth.inMemoryAuthentication().withUser("user").password(bCryptPasswordEncoder.encode("139168698")).roles("USER");
		auth.inMemoryAuthentication().withUser("userAdmin").password(bCryptPasswordEncoder.encode("139168698")).roles("USER","ADMIN");
		auth.inMemoryAuthentication().withUser("admin").password("ad139168698").roles("ADMIN");
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/*
		 * http.formLogin().loginPage("/login");
		 * if we create our login page
		 */
		//use default login form
		http.formLogin();
		//http.httpBasic();
		/*
		 * every http request need authentication*/
		http.authorizeRequests().antMatchers("/save**/**","/delete**/**","/edit**/**","/get**/**","/add**/**").hasRole("ADMIN");
		http.authorizeRequests().anyRequest().authenticated();
		http.csrf();
		/*
		 * disable csrf token( by default is enable)
		 * http.csrf().disable()
		 */
		http.exceptionHandling().accessDeniedPage("/notAuthorized");
	}
	//The return of this function will be used in other part of the application 
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
				
	}
	
}
