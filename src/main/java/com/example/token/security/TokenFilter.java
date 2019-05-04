package com.example.token.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.exception.CustomException;

public class TokenFilter extends OncePerRequestFilter {

	private TokenProvider tokenProvider;

	public TokenFilter(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = tokenProvider.getToken(request);

		try {

			if (token != null && tokenProvider.validate(token)) {
				Authentication auth = tokenProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}

		} catch (CustomException ex) {
			SecurityContextHolder.clearContext();
			response.sendError(ex.getHttpStatus().value(), ex.getMessage());
		}

		//make sure you call this at the end!
		filterChain.doFilter(request, response);

	}

}
