package com.capgemini.webapp.service.api;

import java.util.List;

import com.capgemini.webapp.service.api.model.CustomerBookingModel;
import com.capgemini.webapp.service.api.model.UserInfoModel;
import com.capgemini.webapp.service.api.model.UserModel;

/**
 * @author awarhoka
 *
 */
public interface UserService {

	public UserModel findById(int id);

	public UserModel findBySSO(String sso);

	public boolean saveUser(UserModel userModel);

	public boolean updateUser(UserModel userModel);

	public boolean deleteUserBySSO(String sso);

	public List<UserModel> findAllUsers();

	public boolean isUserSSOUnique(Integer id, String sso);
	
	public List<UserModel> getallProfile();
	
	public void updateList(List<UserInfoModel> lstUser) throws Exception; 
	
	public void uploadUser(List<UserModel> lstUser) throws Exception; 
	
	public String createSSOID (String firstName, String lastName);

	public String getUserDetail(UserModel userModel) ;
	
}