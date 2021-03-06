package com.weather.application.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.weather.application.service.LoginUserInfoService;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private LoginUserInfoService loginUserInfoService;

	@Autowired
	private JwtToken jwtToken;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)

			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;

		String token = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {

			token = requestTokenHeader.substring(7);

			try {

				username = jwtToken.getUsernameFromToken(token);

			} catch (IllegalArgumentException e) {

				log.warn("Unable to get JWT Token");

			} catch (ExpiredJwtException e) {

				log.warn("JWT Token has expired");

			}

		} else {

			log.warn("JWT Token does not begin with Bearer String");

		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.loginUserInfoService.loadUserByUsername(username);

			if (Boolean.TRUE.equals(jwtToken.validateToken(token, userDetails))) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(

						userDetails, null, userDetails.getAuthorities());

				usernamePasswordAuthenticationToken

						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}
}
