package com.weather.application.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatUserRequest {
	
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String city;
	private String state;
	private String country;

}
