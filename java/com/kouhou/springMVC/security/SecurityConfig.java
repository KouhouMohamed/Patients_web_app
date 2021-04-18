package com.kouhou.springMVC.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kouhou.springMVC.security.authentication.UserPrincipaleDetailsService;


@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserPrincipaleDetailsService userDetailsService;
	
	@Bean
	public UserPrincipaleDetailsService userPrincipalService() {
		return new UserPrincipaleDetailsService();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
		daoAuthProvider.setUserDetailsService(userPrincipalService());
		daoAuthProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthProvider;
	}

	// How to search users

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		
		auth.authenticationProvider(authenticationProvider());
		
		
		
		/* In Memory Authentication */
		  //{noop} : no password encoder
			/*
			 * BCryptPasswordEncoder bCryptPasswordEncoder = (BCryptPasswordEncoder) passwordEncoder();
			 * auth.inMemoryAuthentication().withUser("user").password(bCryptPasswordEncoder
			 * .encode("139168698")).roles("USER");
			 * auth.inMemoryAuthentication().withUser("userAdmin").password(
			 * bCryptPasswordEncoder.encode("139168698")).roles("USER","ADMIN");
			 * auth.inMemoryAuthentication().withUser("admin").password("ad139168698").roles
			 * ("ADMIN");
			 */

		
		/*
		 * JDBC Authentication
		 * System.out.println("Crypt "+bCryptPasswordEncoder.encode("139168698"));
		 * auth.jdbcAuthentication() //We define the data base( here we use the same one
		 * in 'application properties' .dataSource(dataSource) //Query to get users from
		 * data base, if columns(username and password and enable) are # to principal
		 * and credentials and active we use 'as'
		 * .usersByUsernameQuery("select username as principal,password as credentials,enable as active from users WHERE username=?"
		 * )
		 * .authoritiesByUsernameQuery("select username as principal, role as role from usersroles where username=?"
		 * ) .passwordEncoder(bCryptPasswordEncoder) .rolePrefix("ROLE_");
		 */
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/*
		 * if we create our login page
		 */
		http.formLogin().loginPage("/login").permitAll();
		/*
		 * use default login form http.formLogin();
		 */
		// http.httpBasic();
		/*
		 * every http request need authentication
		 */
		http.authorizeRequests().antMatchers("/save**/**", "/delete**/**", "/edit**/**", "/get**/**", "/add**/**").hasRole("ADMIN");
		http.authorizeRequests().antMatchers("/list**/**").hasRole("USER");

		/*
		 * every Request need authentication
		 * http.authorizeRequests().anyRequest().authenticated();
		 */
		http.csrf().disable();
		/*
		 * disable csrf token( by default is enable) http.csrf().disable()
		 */
		http.exceptionHandling().accessDeniedPage("/notAuthorized");
		
	}

	// The return of this function will be used in other part of the application
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();

	}

}
