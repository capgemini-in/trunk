package com.capgemini.webapp.common.constants;

/**
 * @author awarhoka
 *
 */
public interface IApplicationConstants {

	String APP_DB_DERIVER = "mywebapp.mysql.driver";
	String APP_DB_URL = "mywebapp.mysql.jdbcUrl";
	String APP_DB_USERNAME = "mywebapp.mysql.username";
	String APP_DB_PASWORD = "mywebapp.mysql.password";

	String HBT_SHOW_SQL = "hibernate.show_sql";
	
	String HBT_USE_2ND_CACHE= "hibernate.cache.use_second_level_cache";
	String HBT_CACHE_RGN_FACTORY_CLS= "hibernate.cache.region.factory_class";
	String HBT_CACHE_PROVIDER= "hibernate.javax.cache.provider";
	
	String SMS_GATEWAY_POST_URL="sms_gateway_post_url";
	String SMS_GATEWAY_AUTH_KEY="sms_gateway_auth_key";
	String AUTH_KEY="authkey";
	String SMS_RESPONSE_TYPE="type";
	String EMAIL_SENDGRID_AUTH_KEY="sendgrid_email_auth_key";
	
	String STATUS_SUCCESS ="success";
	String STATUS_FAILED="failed";
	String REST_API_URL = "http://localhost:8082/pocwebapp";
	//String REST_API_URL = "http://10.48.124.75:8280/AssetManagement/1.0.0";
	
	String STATUS_SUCCESS_CODE="200";
	String STATUS_ERROR_CODE="500";
	String STATUS_NOCONTENT_CODE="401";
	String REST_STATUS="status";
	String REST_MESSAGE="message";
	
	String BUSINESS_NAME = "business_name";
	String BUSINESS_TYPE = "business_type";
	
	String USER_EXIST="User Exist";
	String USER_CREATED="New User Created";
	String DUPLICATE_USER="Duplicate user record. Please check details";
	
	String ROLE_USER = "USER";
	String ROLE_DEALER = "DEALER";
	String ROLE_CUSTOMER = "CUSTOMER";
	String ROLE_DBA = "DBA";
	String ROLE_ADMIN = "ADMIN";
	
	int ROLE_ID_FOR_USER = 1;
	int ROLE_ID_FOR_DEALER = 4;
	int ROLE_ID_FOR_DBA = 3;
	int ROLE_ID_FOR_ADMIN = 2;
	int ROLE_ID_FOR_CUSTOMER = 5;
	
	public static final String LOGIN_FORM_USERNAME_FIELD = "ssoId";
	public static final String LOGIN_FORM_PASSWORD_FIELD = "password";
	public static final String REST_APIGATEWAY_URL="rest.apigateway.uri";
	public static final String AUTHENTICATION_KEY="authentication";
}
