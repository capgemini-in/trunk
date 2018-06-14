package com.capgemini.webapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.webapp.dao.api.UserDao;
import com.capgemini.webapp.service.api.UserService;
import com.capgemini.webapp.service.api.model.UserModel;
import com.capgemini.webapp.dao.api.entity.User;


/**
 * @author awarhoka
 *
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao dao;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public UserModel findById(int id) {
		UserModel usermodel =new DozerBeanMapper().map(dao.findById(id),UserModel.class);
		return usermodel;
	}

	public UserModel findBySSO(String sso) {
		UserModel usermodel =new DozerBeanMapper().map(dao.findBySSO(sso),UserModel.class); 
		return usermodel;
	}
	@Transactional
	public void saveUser(UserModel userModel) {
		userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
		User userEntity =new DozerBeanMapper().map(userModel,User.class);
		dao.save(userEntity);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateUser(UserModel userModel) {
		User userEntity =new DozerBeanMapper().map(userModel,User.class);
		User entity = dao.findById(userEntity.getId());
		
		if(entity!=null){
			entity.setSsoId(userModel.getSsoId());
			if(!userModel.getPassword().equals(entity.getPassword())){
				entity.setPassword(passwordEncoder.encode(userModel.getPassword()));
			}
			entity.setFirstName(userModel.getFirstName());
			entity.setLastName(userModel.getLastName());
			entity.setEmail(userModel.getEmail());
			entity.setUserProfiles(entity.getUserProfiles());
		}
	}

	
	public void deleteUserBySSO(String sso) {
		dao.deleteBySSO(sso);
	}
	@Transactional(readOnly = true)
	public List<UserModel> findAllUsers() {
		List<UserModel> userlist =this.mapList(dao.findAllUsers(), UserModel.class);
		return userlist;
	}
	/*this is Mapper for List*/
    private List<UserModel> mapList(List<User> fromList, final Class<UserModel> toClass) {
	    return fromList
	            .stream()
	            .map(from -> new DozerBeanMapper().map(from, toClass))
	            .collect(Collectors.toList());
	}
	
	public boolean isUserSSOUnique(Integer id, String sso) {
		UserModel userModel = findBySSO(sso);
		return ( userModel == null || ((id != null) && (userModel.getId() == id)));
	}
	
}
