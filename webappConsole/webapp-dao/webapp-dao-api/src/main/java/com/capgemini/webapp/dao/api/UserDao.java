package com.capgemini.webapp.dao.api;

import java.util.List;

import com.capgemini.webapp.dao.api.entity.LdapUser;
import com.capgemini.webapp.dao.api.entity.User;
import com.capgemini.webapp.dao.api.entity.UserInfo;

public interface UserDao {

	User findById(int id);
	
	User findBySSO(String sso);
	
	User save(User user);
	
	void deleteBySSO(String sso);
	
	List<User> findAllUsers();
	
	List<User> UserProfileType();
	
	List<User> findAllLdapUsers();
	
	List<User> findLdapUserGroup();
	
	User findLdapUserBySSO(String sso);
	
	void updateUserInfos(List<UserInfo> p);
	
	void uploadUsers(List<User> p);
	
	void saveLdapUser(LdapUser p);
	
	User findBySSOEmail(String sso, String email);
	
}

