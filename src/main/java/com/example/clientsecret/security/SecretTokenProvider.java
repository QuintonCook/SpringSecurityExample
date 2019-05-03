package com.example.clientsecret.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.clientsecret.security.MyUserDetail;

@Component
public class SecretTokenProvider {

	private final String secret = "secret";

	@Autowired
	private MyUserDetail myUserDetails;

	public UsernamePasswordAuthenticationToken getAuthentication(String id) {

		UserDetails userDetails = myUserDetails.loadUserByUsername(id);
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

	}
	
	public boolean validate(String secret) {
		return secret.equals(this.secret);
	}

	public String resolveToken(HttpServletRequest httpServletRequest) {
		return String.format("%s %s", httpServletRequest.getHeader("clientId"),
				httpServletRequest.getHeader("clientSecret"));
	}

}
