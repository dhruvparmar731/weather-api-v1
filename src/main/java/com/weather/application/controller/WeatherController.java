package com.weather.application.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weather.application.constants.WeatherConstants;
import com.weather.application.service.WeatherService;

@RestController
public class WeatherController {

	@Autowired
	WeatherService weatherService;
	
	@GetMapping("/dashboard")
	public ResponseEntity<String> getWeatherDetails(HttpServletRequest request) {
		
		String weatherResponse = weatherService
				.getWeatherDetailsByCity
				(request.getUserPrincipal().getName());
				
		if (null != weatherResponse) {
			return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
		}
		return new ResponseEntity<>(WeatherConstants.NO_DETAILS_FOR_CITY, HttpStatus.NOT_FOUND);
	}
}
