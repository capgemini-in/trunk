package com.capgemini.webapp.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.webapp.dao.api.UserDao;
import com.capgemini.webapp.dao.api.entity.LdapUser;
import com.capgemini.webapp.dao.api.entity.User;
import com.capgemini.webapp.dao.api.entity.UserProfile;
import com.capgemini.webapp.security.config.LdapUserModel;
import com.capgemini.webapp.service.api.UserService;
import com.capgemini.webapp.service.api.model.UserInfoModel;
import com.capgemini.webapp.service.api.model.UserModel;
import com.capgemini.webapp.service.api.model.UserProfileModel;

@Service
@Transactional
public class LdapUserServiceImpl implements UserService{

	
	@Autowired
	private UserDao dao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	
	@Override
	public UserModel findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserModel findBySSO(String sso) {
		return new DozerBeanMapper().map(dao.findLdapUserBySSO(sso), UserModel.class);
	}

	@Override
	public boolean saveUser(UserModel userModel) {
		
		LdapUserModel p = new LdapUserModel();
		p.setFirstName(userModel.getFirstName());
		p.setLastName(userModel.getLastName());
		p.setUid(userModel.getSsoId());
		p.setEmail(userModel.getEmail());
		p.setPassword(userModel.getPassword());			
		p.setUserProfile(userModel.getUserProfiles());
		LdapUser ldapUser = new DozerBeanMapper().map(p, LdapUser.class);
		//new UserRepository().create(p);
		dao.saveLdapUser(ldapUser);
		return true;
	}

	@Override
	public boolean updateUser(UserModel userModel) {
		User userEntity = new DozerBeanMapper().map(userModel, User.class);
		User entity = dao.findBySSO(userEntity.getSsoId());
		if (entity != null) {
			entity.setSsoId(userModel.getSsoId());
			if (!userModel.getPassword().equals(entity.getPassword())) {
				entity.setPassword(passwordEncoder.encode(userModel.getPassword()));
			}
			entity.setFirstName(userModel.getFirstName());
			entity.setLastName(userModel.getLastName());
			entity.setEmail(userModel.getEmail());
			
			Set<UserProfile> userProfileEntity = getUpdatedUserProfiles(userModel.getUserProfiles(), UserProfile.class);
			entity.setUserProfiles(userProfileEntity);
		}
		return true;
		
	}
	private Set<UserProfile> getUpdatedUserProfiles(Set<UserProfileModel> userProfiles, Class<UserProfile> toClass) {
		return userProfiles.stream().map(from -> new DozerBeanMapper().map(from, toClass)).collect(Collectors.toSet());
	}

	@Override
	public boolean deleteUserBySSO(String sso) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<UserModel> findAllUsers() {
		return this.mapList(dao.findAllLdapUsers(), UserModel.class);
	}

	/**
	 * 
	 * @param fromList
	 * @param toClass
	 * @return
	 */
	private List<UserModel> mapList(List<User> fromList, Class<UserModel> toClass) {
		return fromList.stream().map(from -> new DozerBeanMapper().map(from, toClass)).collect(Collectors.toList());
	}

	@Override
	public boolean isUserSSOUnique(Integer id, String sso) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<UserModel> getallProfile() {
		return this.mapList(dao.findLdapUserGroup(), UserModel.class);
	}

	@Override
	public void updateList(List<UserInfoModel> lstUser) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uploadUser(List<UserModel> lstUser) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String createSSOID(String firstName, String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

			
}
