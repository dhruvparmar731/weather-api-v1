package com.weather.application.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weather.application.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>{

	UserInfo findByUsername(String username);

	boolean existsByUsername(String username);

}
