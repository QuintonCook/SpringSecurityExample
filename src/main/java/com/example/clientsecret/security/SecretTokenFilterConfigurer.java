package com.example.clientsecret.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;

public class SecretTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private SecretTokenProvider secretTokenProvider;

	public SecretTokenFilterConfigurer(SecretTokenProvider secretTokenProvider) {
		this.secretTokenProvider = secretTokenProvider;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		SecretTokenFilter customFilter = new SecretTokenFilter(secretTokenProvider);
		http.addFilterBefore(customFilter, OncePerRequestFilter.class);
	}

}
