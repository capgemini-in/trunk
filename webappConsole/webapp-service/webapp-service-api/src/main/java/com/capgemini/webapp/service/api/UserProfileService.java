package com.capgemini.webapp.service.api;

import java.util.List;

import com.capgemini.webapp.dao.api.entity.UserProfile;


public interface UserProfileService {

	UserProfile findById(int id);

	UserProfile findByType(String type);
	
	List<UserProfile> findAll();
	
}
