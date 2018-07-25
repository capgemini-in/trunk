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
	
	
}
