package com.capgemini.webapp.dao.api;

import java.util.List;
import com.capgemini.webapp.dao.api.entity.User;
import com.capgemini.webapp.dao.api.entity.UserInfo;


public interface UserDao {

	User findById(int id);
	
	User findBySSO(String sso);
	
	void save(User user);
	
	void deleteBySSO(String sso);
	
	List<User> findAllUsers();
	
	List<User> UserProfileType();
	
	List<User> findAllLdapUsers();
	
	List<User> findLdapUserGroup();
	
	void updateUserInfos(List<UserInfo> p);
	
}

