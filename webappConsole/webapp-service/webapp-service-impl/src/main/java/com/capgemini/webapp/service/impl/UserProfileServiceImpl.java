package com.capgemini.webapp.service.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.webapp.dao.api.UserProfileDao;
import com.capgemini.webapp.dao.api.entity.UserProfile;
import com.capgemini.webapp.service.api.UserProfileService;
import com.capgemini.webapp.service.api.model.UserProfileModel;

@Service("userProfileService")
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	UserProfileDao dao;

	public UserProfileModel findById(int id) {
		UserProfileModel userProfileModel = new DozerBeanMapper().map(dao.findById(id), UserProfileModel.class);
		return userProfileModel;
	}

	public UserProfileModel findByType(String type) {
		UserProfileModel userProfileModel = new DozerBeanMapper().map(dao.findByType(type), UserProfileModel.class);
		return userProfileModel;
	}

	public List<UserProfileModel> findAll() {
		return this.mapList(dao.findAll(), UserProfileModel.class);
	}

	/* this is Mapper for List */
	private List<UserProfileModel> mapList(List<UserProfile> fromList, final Class<UserProfileModel> toClass) {
		return fromList.stream().map(from -> new DozerBeanMapper().map(from, toClass)).collect(Collectors.toList());
	}

	@Override
	public boolean saveUserProfile(UserProfileModel userProfileModelObject) throws org.hibernate.exception.ConstraintViolationException {
		boolean isCreated = false;
		UserProfile userProfile = new DozerBeanMapper().map(userProfileModelObject, UserProfile.class);
		try {
			UserProfileModel userProfileModel = new DozerBeanMapper().map(dao.saveUserProfile(userProfile),
					UserProfileModel.class);
			if (userProfileModel != null) {
				isCreated = true;
			}
		}
		catch (Exception e) {
			if (e instanceof org.hibernate.exception.ConstraintViolationException) {
				org.hibernate.exception.ConstraintViolationException e1 = (org.hibernate.exception.ConstraintViolationException) e;
				throw new org.hibernate.exception.ConstraintViolationException(e1.getConstraintName(), e1.getSQLException(),e1.getLocalizedMessage());
			}
			isCreated = false;
		}
		return isCreated;
	}

	@Override
	public boolean deleteRoleByProfileID(String userProfileId) throws org.hibernate.exception.ConstraintViolationException{
		boolean isDeleted = false;
		try {
			dao.deleteByProfileID(userProfileId);
			isDeleted = true;

		} catch (Exception e) {
			if (e instanceof org.hibernate.exception.ConstraintViolationException) {
				org.hibernate.exception.ConstraintViolationException e1 = (org.hibernate.exception.ConstraintViolationException) e;
				throw new org.hibernate.exception.ConstraintViolationException(e1.getConstraintName(), e1.getSQLException(),e1.getLocalizedMessage());
			}
			isDeleted = false;
		}
		return isDeleted;
	}

	@Override
	public boolean isUserExistForRole(int profileId) {
		boolean isExist = false;
		try {
			isExist = dao.isUserExistForRole(profileId);

		} catch (Exception e) {
			isExist = false;
		}
		return isExist;
	}
}
