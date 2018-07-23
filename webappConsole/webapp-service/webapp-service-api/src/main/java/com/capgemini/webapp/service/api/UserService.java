package com.capgemini.webapp.service.api;

import java.util.List;

import com.capgemini.webapp.service.api.model.UserInfoModel;
import com.capgemini.webapp.service.api.model.UserModel;

/**
 * @author awarhoka
 *
 */
public interface UserService {

	UserModel findById(int id);

	UserModel findBySSO(String sso);

	boolean saveUser(UserModel userModel);

	boolean updateUser(UserModel userModel);

	boolean deleteUserBySSO(String sso);

	List<UserModel> findAllUsers();

	boolean isUserSSOUnique(Integer id, String sso);
	
	List<UserModel> getallProfile();
	
	void updateList(List<UserInfoModel> lstUser) throws Exception; 
	
	void uploadUser(List<UserModel> lstUser) throws Exception; 

}