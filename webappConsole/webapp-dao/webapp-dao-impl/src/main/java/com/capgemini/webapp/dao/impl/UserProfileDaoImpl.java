package com.capgemini.webapp.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.capgemini.webapp.common.utils.LoggingMessages;
import com.capgemini.webapp.dao.api.UserProfileDao;
import com.capgemini.webapp.dao.api.entity.UserProfile;

@Repository("userProfileDao")
public class UserProfileDaoImpl extends AbstractDao<Integer, UserProfile> implements UserProfileDao {

	static final Logger logger = LoggerFactory.getLogger(UserProfileDaoImpl.class);
	
	public UserProfile findById(int id) {
		logger.info(LoggingMessages.getMessage("UserProfile.FIND"), "ID",id);
		return getByKey(id);
	}

	public UserProfile findByType(String type) {
		logger.info(LoggingMessages.getMessage("UserProfile.FIND"), "TYPE",type);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("type", type));
		return (UserProfile) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<UserProfile> findAll() {
		logger.info(LoggingMessages.getMessage("UserProfile.ALL"));
		Criteria crit = createEntityCriteria();
		crit.addOrder(Order.asc("type"));
		return (List<UserProfile>) crit.list();
	}

	@Override
	public UserProfile saveUserProfile(UserProfile userProfile) {
		logger.info(LoggingMessages.getMessage("UserProfile.CREATE"),userProfile.getType());
		getSession().save(userProfile);
		logger.info(LoggingMessages.getMessage("UserProfile.CREATED"),userProfile.getType(),userProfile.getId());
		return userProfile;
	}

	@Override
	public void deleteByProfileID(String userProfileId) {
		logger.info(LoggingMessages.getMessage("UserProfile.DELETE"), userProfileId);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", Integer.valueOf(userProfileId)));
		UserProfile userProfile = (UserProfile) crit.uniqueResult();
		delete(userProfile);
		logger.info(LoggingMessages.getMessage("UserProfile.DELETED"),userProfileId);
	}

	@Override
	public boolean isUserExistForRole(int profileId) {
		logger.info(LoggingMessages.getMessage("UserProfile.Query"), profileId);
		boolean isUserExist =false;
		List<BigInteger> resultCount = getSession().createSQLQuery("Select count(*) from APP_USER_USER_PROFILE where user_profile_id="+ profileId).list();
		BigInteger count = resultCount.get(0);
		if(resultCount != null && resultCount.size() > 0 && count.intValue() > 0)
			isUserExist = true;
		return isUserExist;

	}
}
