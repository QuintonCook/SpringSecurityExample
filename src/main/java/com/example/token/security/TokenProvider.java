package com.example.token.security;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.clientsecret.security.MyUserDetail;
import com.example.exception.CustomException;
import com.example.model.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenProvider {

	@Autowired
	MyUserDetail myUserDetail;
	
	private long validityInMilliseconds = 360000L;

	/*
	 * Authorization:Bearer [JSON WEB TOKEN]
	 */

	private String theKey = "keyed!!!";

	  public String grantToken(String username, List<Role> roles) {

		    Claims claims = Jwts.claims().setSubject(username);
		    claims.put("auth", roles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority())).filter(Objects::nonNull).collect(Collectors.toList()));

		    Date now = new Date();
		    Date validity = new Date(now.getTime() + validityInMilliseconds);
		    
		    return Jwts.builder()//
		        .setClaims(claims)//
		        .setIssuedAt(now)//
		        .setExpiration(validity)//
		        .signWith(SignatureAlgorithm.HS256, theKey)//
		        .compact();
		  }

	
	public String getToken(HttpServletRequest request) {
		String tmp = request.getHeader("Authorization");

		if (tmp != null && tmp.startsWith("Bearer ")) {
			return tmp.substring(7);
		}

		return null;
	}

	public Authentication getAuthentication(String token) {
		UserDetails user = myUserDetail.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	}

	public boolean validate(String token) {

		try {
			Jwts.parser().setSigningKey(theKey).parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			e.printStackTrace();
			throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(theKey).parseClaimsJws(token).getBody().getSubject();
	}

}
