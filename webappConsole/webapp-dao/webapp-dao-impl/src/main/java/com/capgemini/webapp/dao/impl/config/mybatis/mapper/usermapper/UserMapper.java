package com.capgemini.webapp.dao.impl.config.mybatis.mapper.usermapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.capgemini.webapp.dao.api.entity.User;
import com.capgemini.webapp.dao.api.entity.UserProfile;

public interface UserMapper extends IUserQueryConstants{
	
	@Select(GET_USER)
	@Results({
        @Result(property = "id", column = "id"),
        @Result(property = "ssoId", column = "sso_id"),
        @Result(property = "password", column = "password"),
        @Result(property = "firstName", column = "first_name"),
        @Result(property = "lastName", column = "last_name"),
        @Result(property = "email", column = "email")
      })
	public User retrieveUserBySSOID(@Param("ssoId") String ssoId);
	
	public void insertUser(User user);
	public User getUserById(Integer userId);
	public List<User> getAllUsers();
	public void updateUser(User user);
	public void deleteUser(Integer userId);
	
	@Select(GET_USER_PROFILE)
	public Set<UserProfile> getUserProfile(@Param("user_id") Integer user_id);
	/*
	 *	@Select(GET_USER)
	@Results(value = {
        @Result(property = "id", column = "id"),
        @Result(property = "ssoId", column = "sso_id"),
        @Result(property = "password", column = "password"),
        @Result(property = "firstName", column = "first_name"),
        @Result(property = "lastName", column = "last_name"),
        @Result(property="userProfiles", javaType=List.class, column="pid", many=@Many(select ="getUserProfile"))
      })
	public User retrieveUserBySSOID(@Param("ssoId") String ssoId);
	
	@Select(GET_USER_PROFILE)
	public UserProfile getUserProfile(@Param("user_id") String id);
	
	public void insertUser(User user);
	public User getUserById(Integer userId);
	public List<User> getAllUsers();
	public void updateUser(User user);
	public void deleteUser(Integer userId);*/
	
}
