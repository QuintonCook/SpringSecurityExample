package com.example.clientsecret.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.exception.CustomException;

public class SecretTokenFilter extends OncePerRequestFilter {

	SecretTokenProvider secretTokenProvider;

	public SecretTokenFilter(SecretTokenProvider provider) {
		this.secretTokenProvider = provider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		
		String token = secretTokenProvider.resolveToken(httpServletRequest);
		String id = token.split(" ")[0];
		String secret = token.split(" ")[1];
		
		try {
			if (token != null && secretTokenProvider.validate(secret)) {
				Authentication auth = secretTokenProvider.getAuthentication(id);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (CustomException ex) {
			// this is very important, since it guarantees the user is not authenticated at
			// all
			SecurityContextHolder.clearContext();
			httpServletResponse.sendError(ex.getHttpStatus().value(), ex.getMessage());
			return;
		}

		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

}
