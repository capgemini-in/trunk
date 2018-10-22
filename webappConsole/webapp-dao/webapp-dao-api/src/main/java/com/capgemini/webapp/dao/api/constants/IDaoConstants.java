package com.capgemini.webapp.dao.api.constants;

/**
 * @author awarhoka
 *
 */
public interface IDaoConstants {

	public static final  String APP_DB_DERIVER = "mywebapp.mysql.driver";
	public static final  String APP_DB_URL = "mywebapp.mysql.jdbcUrl";
	public static final  String APP_DB_USERNAME = "mywebapp.mysql.username";
	public static final  String APP_DB_PASWORD = "mywebapp.mysql.password";

	public static final  String HBT_SHOW_SQL = "hibernate.show_sql";
	
	public static final  String HBT_USE_2ND_CACHE= "hibernate.cache.use_second_level_cache";
	public static final  String HBT_CACHE_RGN_FACTORY_CLS= "hibernate.cache.region.factory_class";
	public static final  String HBT_CACHE_PROVIDER= "hibernate.javax.cache.provider";
	
	public static final String JDBC_DRIVER_CLASSNAME = "jdbc.driverClassName";
	public static final String JDBC_URL = "jdbc.url";
	public static final String JDBC_USERNAME = "jdbc.username";
	public static final String JDBC_PASSWORD = "jdbc.password";
	
	// Hibernate properties
	public static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	public static final String HIBERNATE_DIALECT = "hibernate.dialect";
	public static final String HIBERNATE_FORMAT_SQL= "hibernate.format_sql";
	
	public static final String HIBERNATE_C3P0_MIN_SIZE = "hibernate.c3p0.min_size";
	public static final String HIBERNATE_C3P0_MAX_SIZE= "hibernate.c3p0.max_size";
	public static final String HIBERNATE_C3P0_ACQUIRE_INCREMENT= "hibernate.c3p0.acquire_increment";
	public static final String HIBERNATE_C3P0_TIMEOUT= "hibernate.c3p0.timeout";
	public static final String HIBERNATE_C3P0_MAX_STATEMENTS= "hibernate.c3p0.max_statements";

}
