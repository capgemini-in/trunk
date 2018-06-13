package com.capgemini.webapp.service.api;

import java.util.List;

import com.capgemini.webapp.dao.api.entity.User;


public interface UserService {
	
	User findById(int id);
	
	User findBySSO(String sso);
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUserBySSO(String sso);

	List<User> findAllUsers(); 
	
	boolean isUserSSOUnique(Integer id, String sso);

}