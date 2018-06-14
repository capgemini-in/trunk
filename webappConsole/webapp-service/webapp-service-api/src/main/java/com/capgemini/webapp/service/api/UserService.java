package com.capgemini.webapp.service.api;

import java.util.List;

import com.capgemini.webapp.service.api.model.UserModel;

/**
 * @author awarhoka
 *
 */
public interface UserService {

	UserModel findById(int id);

	UserModel findBySSO(String sso);

	void saveUser(UserModel userModel);

	void updateUser(UserModel userModel);

	void deleteUserBySSO(String sso);

	List<UserModel> findAllUsers();

	boolean isUserSSOUnique(Integer id, String sso);

}