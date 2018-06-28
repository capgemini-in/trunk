package com.capgemini.webapp.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.webapp.dao.api.UserDao;
import com.capgemini.webapp.dao.api.entity.User;
import com.capgemini.webapp.dao.api.entity.UserInfo;
import com.capgemini.webapp.dao.api.entity.UserProfile;
import com.capgemini.webapp.service.api.UserService;
import com.capgemini.webapp.service.api.model.UserInfoModel;
import com.capgemini.webapp.service.api.model.UserModel;
import com.capgemini.webapp.service.api.model.UserProfileModel;

/**
 * @author awarhoka
 *
 */
@Service("userService")
@Transactional
@PropertySource("classpath:/config/ldap/ldap_details.properties")
public class UserServiceImpl implements UserService {

	@Autowired
	private Environment env;

	@Autowired
	private UserDao dao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserModel findById(int id) {
		UserModel usermodel = new DozerBeanMapper().map(dao.findById(id), UserModel.class);
		return usermodel;
	}

	public UserModel findBySSO(String sso) {
		UserModel usermodel = null;
		User user = null;
		String authentication = env.getRequiredProperty("authentication");
		if (authentication.equalsIgnoreCase("LDAP")) {
			user = dao.findLdapUserBySSO(sso);
		} else {
			user = dao.findBySSO(sso);
		}

		if (user != null) {
			usermodel = new DozerBeanMapper().map(user, UserModel.class);
		}
		return usermodel;
	}

	@Transactional
	public void saveUser(UserModel userModel) {
		userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
		User userEntity = new DozerBeanMapper().map(userModel, User.class);
		dao.save(userEntity);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate
	 * update explicitly. Just fetch the entity from db and update it with proper
	 * values within transaction. It will be updated in db once transaction ends.
	 */
	public void updateUser(UserModel userModel) {
		User userEntity = new DozerBeanMapper().map(userModel, User.class);
		User entity = dao.findById(userEntity.getId());

		if (entity != null) {
			entity.setSsoId(userModel.getSsoId());
			if (!userModel.getPassword().equals(entity.getPassword())) {
				entity.setPassword(passwordEncoder.encode(userModel.getPassword()));
			}
			entity.setFirstName(userModel.getFirstName());
			entity.setLastName(userModel.getLastName());
			entity.setEmail(userModel.getEmail());

			// Set<UserProfile> userProfileEntity =new
			// DozerBeanMapper().map(userModel.getUserProfiles(),Set.class);
			Set<UserProfile> userProfileEntity = getUpdatedUserProfiles(userModel.getUserProfiles(), UserProfile.class);
			entity.setUserProfiles(userProfileEntity);
		}
	}

	/**
	 * 
	 * @param userProfiles
	 * @param toClass
	 * @return
	 */
	private Set<UserProfile> getUpdatedUserProfiles(Set<UserProfileModel> userProfiles, Class<UserProfile> toClass) {
		return userProfiles.stream().map(from -> new DozerBeanMapper().map(from, toClass)).collect(Collectors.toSet());
	}

	public void deleteUserBySSO(String sso) {
		dao.deleteBySSO(sso);
	}

	@Transactional(readOnly = true)
	public List<UserModel> findAllUsers() {
		// List<UserModel> userlist =this.mapList(dao.findAllUsers(), UserModel.class);
		// return userlist;
		List<UserModel> userlist = null;
		String authentication = env.getRequiredProperty("authentication");
		if (authentication.equalsIgnoreCase("LDAP")) {
			// return dao.findAllLdapUsers();
			userlist = this.mapList(dao.findAllLdapUsers(), UserModel.class);
		} else {
			// dao.findAllUsers();
			userlist = this.mapList(dao.findAllUsers(), UserModel.class);
		}
		return userlist;
	}

	/* this is Mapper for List */
	private List<UserModel> mapList(List<User> fromList, final Class<UserModel> toClass) {
		return fromList.stream().map(from -> new DozerBeanMapper().map(from, toClass)).collect(Collectors.toList());
	}

	public boolean isUserSSOUnique(Integer id, String sso) {
		UserModel userModel = findBySSO(sso);
		return (userModel == null || ((id != null) && (userModel.getId() == id)));
	}

	@Override
	public List<UserModel> getallProfile() {
		List<UserModel> userlist = null;
		String authentication = env.getRequiredProperty("authentication");
		if (authentication.equalsIgnoreCase("LDAP")) {
			// return dao.findLdapUserGroup();
			userlist = this.mapList(dao.findLdapUserGroup(), UserModel.class);
		} else {
			// return dao.UserProfileType();
			userlist = this.mapList(dao.UserProfileType(), UserModel.class);
		}
		return userlist;
	}

	@Override
	@Transactional
	public void updateList(List<UserInfoModel> p) throws Exception {
		List<UserInfo> userlist = this.mapuserInfoList(p, UserInfo.class);
		dao.updateUserInfos(userlist);
	}

	private List<UserInfo> mapuserInfoList(List<UserInfoModel> fromList, final Class<UserInfo> toClass) {
		return fromList.stream().map(from -> new DozerBeanMapper().map(from, toClass)).collect(Collectors.toList());
	}
}
