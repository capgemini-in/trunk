package com.capgemini.webapp.dao.api;

import java.util.List;

import com.capgemini.webapp.dao.api.entity.UserProfile;


public interface UserProfileDao {

	List<UserProfile> findAll();
	
	UserProfile findByType(String type);
	
	UserProfile findById(int id);
}
