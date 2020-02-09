package com.weather.application.controller;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.weather.application.dao.UserInfoRepository;
import com.weather.application.entity.UserInfo;
import com.weather.application.model.CreatUserRequest;
import com.weather.application.service.LoginUserInfoService;

@RestController
public class UserInfoController {
	
	@Autowired
	UserInfoRepository userInfoRepository;
	
	@Autowired
	LoginUserInfoService userInfoService;
	
	
	@PostMapping("/create-user")
	public ResponseEntity<UserInfo> createUser(@RequestBody @Valid CreatUserRequest creatUserRequest) {
		
		String username = creatUserRequest.getUsername();
        if (userInfoRepository.existsByUsername(username)){
            throw new ValidationException("Username already existed");
        }

        UserInfo userInfoEntity = new UserInfo
        			(creatUserRequest.getFirstName(),
        					creatUserRequest.getLastName(),
        					creatUserRequest.getUsername(),
        					new BCryptPasswordEncoder().encode(creatUserRequest.getPassword()),
        					creatUserRequest.getCity(),
        					creatUserRequest.getState(),
        					creatUserRequest.getCountry());
        
        userInfoRepository.save(userInfoEntity);
        
        return new ResponseEntity<UserInfo>(userInfoEntity, HttpStatus.CREATED);
		
	}
	

}
