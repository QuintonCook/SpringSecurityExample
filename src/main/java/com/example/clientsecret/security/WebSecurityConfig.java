package com.example.clientsecret.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.example.token.security.TokenFilterConfigurer;
import com.example.token.security.TokenProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	TokenProvider tokenProvider;

	@Autowired
	SecretTokenProvider secretTokenProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Disable CSRF (cross site request forgery)
		http.csrf().disable();

		// No session will be created or used by spring security
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Entry points
		http.authorizeRequests()//
				.antMatchers("/h2-console").permitAll()
				.antMatchers("/login").permitAll()
				// Disallow everything else..
        		.anyRequest().authenticated();

		// Apply Token this is the client secret config that works
		//http.apply(new SecretTokenFilterConfigurer(secretTokenProvider));
		
		http.apply(new TokenFilterConfigurer(tokenProvider));
	}

}