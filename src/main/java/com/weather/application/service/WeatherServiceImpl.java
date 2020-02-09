package com.weather.application.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.weather.application.constants.WeatherConstants;
import com.weather.application.dao.UserInfoRepository;
import com.weather.application.entity.UserInfo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WeatherServiceImpl implements WeatherService {

	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	@Override
	public String getCity(String name) {
		UserInfo info = userInfoRepository.findByUsername(name);
		return info.getCity();
	}

	@Override
	public String prepareUrl(String city) {
		return WeatherConstants.HOST + city + WeatherConstants.APP_ID;
	}

	@Override
	public String getWeatherDetailsByCity(String name) {

		String city = getCity(name);
		final String cityUrl = prepareUrl(city);

		try {

			RestTemplate restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(500))
					.setReadTimeout(Duration.ofSeconds(500)).build();

			ResponseEntity<String> weatherResponse = restTemplate.getForEntity(cityUrl, String.class);

			if (weatherResponse.getStatusCodeValue() == 200) {
				return weatherResponse.getBody();
			}

		} catch (HttpStatusCodeException e) {
			log.error(e.getMessage());
		}

		return null;
	}

}
