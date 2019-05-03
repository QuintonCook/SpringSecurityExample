package com.example.token.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;


public class TokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	public TokenFilterConfigurer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		TokenFilter customFilter = new TokenFilter();
		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
