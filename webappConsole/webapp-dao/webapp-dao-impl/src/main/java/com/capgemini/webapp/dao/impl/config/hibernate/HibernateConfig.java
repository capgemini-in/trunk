package com.capgemini.webapp.dao.impl.config.hibernate;

import static org.hibernate.cfg.AvailableSettings.C3P0_ACQUIRE_INCREMENT;
import static org.hibernate.cfg.AvailableSettings.C3P0_MAX_SIZE;
import static org.hibernate.cfg.AvailableSettings.C3P0_MAX_STATEMENTS;
import static org.hibernate.cfg.AvailableSettings.C3P0_MIN_SIZE;
import static org.hibernate.cfg.AvailableSettings.C3P0_TIMEOUT;
import static org.hibernate.cfg.AvailableSettings.CACHE_REGION_FACTORY;
import static org.hibernate.cfg.AvailableSettings.SHOW_SQL;
import static org.hibernate.cfg.AvailableSettings.USE_SECOND_LEVEL_CACHE;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.capgemini.webapp.dao.api.constants.IDaoConstants;;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.capgemini.webapp.dao.api.entity" })
@PropertySource(value = { "classpath:/config/database/myappdb_dev.properties" }, ignoreResourceNotFound = true)
public class HibernateConfig {

	@Autowired
	private Environment env;

	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setHibernateProperties(getHibernateProperties());
		sessionFactory.setDataSource(getDataSource());
		sessionFactory.setPackagesToScan(new String[] { "com.capgemini.webapp.dao.api.entity" });

		return sessionFactory;
	}

	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty(IDaoConstants.JDBC_DRIVER_CLASSNAME));
		dataSource.setUrl(env.getRequiredProperty(IDaoConstants.JDBC_URL));
		dataSource.setUsername(env.getRequiredProperty(IDaoConstants.JDBC_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(IDaoConstants.JDBC_PASSWORD));
		return dataSource;
	}

	private Properties getHibernateProperties() {
		Properties props = new Properties();

		// Setting JDBC properties
		/*
		 * props.put(DRIVER, env.getProperty("jdbc.driverClassName")); props.put(URL,
		 * env.getProperty("jdbc.url")); props.put(USER,
		 * env.getProperty("jdbc.username")); props.put(PASS,
		 * env.getProperty("jdbc.password"));
		 */

		// Setting Hibernate properties
		props.put(SHOW_SQL, env.getProperty(IDaoConstants.HIBERNATE_SHOW_SQL));
		// props.put(HBM2DDL_AUTO, env.getProperty("hibernate.hbm2ddl.auto"));

		// Setting C3P0 properties
		props.put(C3P0_MIN_SIZE, env.getProperty(IDaoConstants.HIBERNATE_C3P0_MIN_SIZE));
		props.put(C3P0_MAX_SIZE, env.getProperty(IDaoConstants.HIBERNATE_C3P0_MAX_SIZE));
		props.put(C3P0_ACQUIRE_INCREMENT, env.getProperty(IDaoConstants.HIBERNATE_C3P0_ACQUIRE_INCREMENT));
		props.put(C3P0_TIMEOUT, env.getProperty(IDaoConstants.HIBERNATE_C3P0_TIMEOUT));
		props.put(C3P0_MAX_STATEMENTS, env.getProperty(IDaoConstants.HIBERNATE_C3P0_MAX_STATEMENTS));
		props.put(USE_SECOND_LEVEL_CACHE, env.getProperty(IDaoConstants.HBT_USE_2ND_CACHE));
		props.put(CACHE_REGION_FACTORY, env.getProperty(IDaoConstants.HBT_CACHE_RGN_FACTORY_CLS));
		props.put(IDaoConstants.HBT_CACHE_PROVIDER, env.getProperty(IDaoConstants.HBT_CACHE_PROVIDER));
		return props;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager getTransactionManager(SessionFactory s) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(s);
		return txManager;
	}

	/**
	 * DEVELOPER PROFILE (activate with: spring.profiles.active=developer)
	 */
	/*
	 * @Configuration
	 * 
	 * @Profile("developer")
	 * 
	 * @PropertySources({
	 * 
	 * @PropertySource(value = "classpath:META-INF/config/mts-default.properties",
	 * ignoreResourceNotFound = false),
	 * 
	 * @PropertySource(value = "classpath:mts-env.properties",
	 * ignoreResourceNotFound = true),
	 * 
	 * @PropertySource(value =
	 * "classpath:META-INF/config/local/mts-local.properties",
	 * ignoreResourceNotFound = true),}) static class DeveloperProps { }
	 */
}
