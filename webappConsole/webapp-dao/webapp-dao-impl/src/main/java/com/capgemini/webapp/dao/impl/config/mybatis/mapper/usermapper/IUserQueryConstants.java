package com.capgemini.webapp.dao.impl.config.mybatis.mapper.usermapper;

public interface IUserQueryConstants {
	final String GET_USER ="SELECT id,sso_id,password,first_name,last_name,email FROM APP_USER WHERE SSO_ID =#{ssoId}";
	
	/*final String GET_USER = new StringBuffer(
			"SELECT usr.id,usr.sso_id,usr.password,usr.first_name,usr.last_name,usr.email,")
					.append("profile.id as pro_id ,profile.type FROM APP_USER usr ")
					.append("LEFT JOIN APP_USER_USER_PROFILE user_profile ON  usr.id =user_profile.user_id ")
					.append("LEFT JOIN USER_PROFILE profile ON  profile.id = user_profile.user_profile_id ")
					.append("WHERE usr.SSO_ID =#{ ssoId }").toString();

	final String GET_USER_DETAILS = "SELECT usr.id,usr.sso_id,usr.password,usr.first_name,usr.last_name,usr.email,"
			+ "profile.id as pid ,profile.type FROM APP_USER usr "
			+ "LEFT JOIN APP_USER_USER_PROFILE user_profile ON  usr.id =user_profile.user_id "
			+ "LEFT JOIN USER_PROFILE profile ON  profile.id = user_profile.user_profile_id "
			+ "WHERE usr.SSO_ID =#{ ssoId }";*/
	
	final String GET_USER_PROFILE="SELECT profile.id, profile.type FROM APP_USER_USER_PROFILE user_profile "+ 
			"LEFT JOIN USER_PROFILE profile ON  user_profile.user_profile_id = profile.id "+
			"WHERE user_profile.user_id =#{user_id}";
}
