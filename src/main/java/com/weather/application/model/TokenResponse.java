package com.weather.application.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4257388289085129423L;
	
	private final String jwttoken;
}
