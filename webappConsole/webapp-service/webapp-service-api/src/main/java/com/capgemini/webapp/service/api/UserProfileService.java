package com.capgemini.webapp.service.api;

import java.util.List;

import com.capgemini.webapp.service.api.model.UserProfileModel;


/**
 * @author awarhoka
 *
 */
public interface UserProfileService {

	UserProfileModel findById(int id);

	UserProfileModel findByType(String type);
	
	List<UserProfileModel> findAll();
	
}
