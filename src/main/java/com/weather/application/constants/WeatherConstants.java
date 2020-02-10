package com.weather.application.constants;

public class WeatherConstants {
	
	public static final String HOST = "http://api.openweathermap.org/data/2.5/weather?q=";
	public static final String APP_ID = "&appid=4a28dab65f8fa490da91ec069b996c05";
	public static final String NO_DETAILS_FOR_CITY = "No Weather Details found for this city.";
	public static final long JWT_TOKEN_VALIDITY = 3 * 60 * 60;
	
	private WeatherConstants() {
	}
}
