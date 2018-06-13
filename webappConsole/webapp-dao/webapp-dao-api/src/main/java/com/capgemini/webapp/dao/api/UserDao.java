package com.capgemini.webapp.dao.api;

import java.util.List;

import com.capgemini.webapp.dao.api.entity.User;


public interface UserDao {

	User findById(int id);
	
	User findBySSO(String sso);
	
	void save(User user);
	
	void deleteBySSO(String sso);
	
	List<User> findAllUsers();

}

