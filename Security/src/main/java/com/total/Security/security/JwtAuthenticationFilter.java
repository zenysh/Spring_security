package com.total.Security.security;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.total.Security.utils.CONSTANTS;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userdetailservice;

	@Autowired
	private JwtTokenProvider tokenprovider;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		String header = req.getHeader(CONSTANTS.HEADER_STRING);
		String username = null;

		String authToken = null;
		if (header != null && header.startsWith(CONSTANTS.TOKEN_PREFIX)) {
			authToken = header.replace(CONSTANTS.TOKEN_PREFIX, "");
			try {
				username = tokenprovider.getUsernameFromToken(authToken);
			} catch (IllegalArgumentException e) {
				logger.error("An error occured while getting username From Token", e);
			} catch (ExpiredJwtException e) {
				logger.error("A token is expired and not valid anymore", e);
			} catch (SignatureException e) {
				logger.error("Authentication Failed. A username or password is not valid", e);
			}
		} else {
			logger.warn("Could not find bearer in header. So it will ignore the header");
		}
	
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = userdetailservice.loadUserByUsername(username);

			/*
			 * if (provider.validateTokens(authToken, userDetails )) {
			 * UsernamePasswordAuthenticationToken authentication = new
			 * UsernamePasswordAuthenticationToken( userDetails, null, Arrays.asList(new
			 * SimpleGrantedAuthority("ADMIN"))); authentication.setDetails(new
			 * WebAuthenticationDetailsSource().buildDetails(req));
			 * logger.info("authenticated user " + username + ", setting security context");
			 * SecurityContextHolder.getContext().setAuthentication(authentication); }
			 */
			
				if (tokenprovider.ValidateToken(authToken, userDetails)) {
					UsernamePasswordAuthenticationToken authentication = tokenprovider.getAuthentication(authToken,
							SecurityContextHolder.getContext().getAuthentication(), userDetails);
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
					logger.info("authenticated user " + username + ", setting security context");
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		chain.doFilter(req, res);

	}

}
