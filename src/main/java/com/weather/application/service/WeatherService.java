package com.weather.application.service;

public interface WeatherService {

	String getCity(String name);

	String prepareUrl(String city);

	String getWeatherDetailsByCity(String name);

}
