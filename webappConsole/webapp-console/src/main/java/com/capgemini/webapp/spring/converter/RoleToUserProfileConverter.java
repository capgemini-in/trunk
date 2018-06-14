package com.capgemini.webapp.spring.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.capgemini.webapp.service.api.UserProfileService;
import com.capgemini.webapp.service.api.model.UserProfileModel;

/**
 * A converter class used in views to map id's to actual userProfile objects.
 */
@Component
public class RoleToUserProfileConverter implements Converter<Object, UserProfileModel>{

	static final Logger logger = LoggerFactory.getLogger(RoleToUserProfileConverter.class);
	
	@Autowired
	UserProfileService userProfileService;

	/**
	 * Gets UserProfileModel by Id
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	public UserProfileModel convert(Object element) {
		Integer id = Integer.parseInt((String)element);
		UserProfileModel profile= userProfileService.findById(id);
		logger.info("Profile : {}",profile);
		return profile;
	}
	
}