package com.weather.application.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.weather.application.config.JwtToken;
import com.weather.application.model.LoginRequest;
import com.weather.application.model.TokenResponse;
import com.weather.application.service.AuthenticateService;
import com.weather.application.service.LoginUserInfoService;

@RestController
public class LoginController {

	@Autowired
	AuthenticateService authenticateService;

	@Autowired
	LoginUserInfoService loginUserInfoService;

	@Autowired
	private JwtToken jwtToken;

	@PostMapping("/authenticate")
	public ResponseEntity<TokenResponse> createAuthenticationToken(@RequestBody @Valid LoginRequest loginRequest)
			throws Exception {
		
		authenticateService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
		
		final UserDetails userDetails = loginUserInfoService

				.loadUserByUsername(loginRequest.getUsername());

		final String token = jwtToken.generateToken(userDetails);

		return new ResponseEntity<TokenResponse>(new TokenResponse(token), HttpStatus.OK);
	}

}
