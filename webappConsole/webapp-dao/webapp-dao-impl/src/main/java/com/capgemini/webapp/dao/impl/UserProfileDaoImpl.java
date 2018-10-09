package com.capgemini.webapp.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.capgemini.webapp.dao.api.UserProfileDao;
import com.capgemini.webapp.dao.api.entity.UserProfile;

@Repository("userProfileDao")
public class UserProfileDaoImpl extends AbstractDao<Integer, UserProfile> implements UserProfileDao {

	public UserProfile findById(int id) {
		return getByKey(id);
	}

	public UserProfile findByType(String type) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("type", type));
		return (UserProfile) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<UserProfile> findAll() {
		Criteria crit = createEntityCriteria();
		crit.addOrder(Order.asc("type"));
		return (List<UserProfile>) crit.list();
	}

	@Override
	public UserProfile saveUserProfile(UserProfile userProfile) {
		getSession().save(userProfile);
		return userProfile;
	}

	@Override
	public void deleteByProfileID(String userProfileId) {
		// TODO Auto-generated method stub
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", Integer.valueOf(userProfileId)));
		UserProfile userProfile = (UserProfile) crit.uniqueResult();
		delete(userProfile);
	}

	@Override
	public boolean isUserExistForRole(int profileId) {
		boolean isUserExist =false;
		List<BigInteger> resultCount = getSession().createSQLQuery("Select count(*) from APP_USER_USER_PROFILE where user_profile_id="+ profileId).list();
		BigInteger count = resultCount.get(0);
		if(resultCount != null && resultCount.size() > 0 && count.intValue() > 0)
			isUserExist = true;
		return isUserExist;

	}
}
