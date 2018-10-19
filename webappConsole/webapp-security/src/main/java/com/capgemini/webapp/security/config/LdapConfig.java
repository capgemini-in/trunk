package com.capgemini.webapp.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import com.capgemini.webapp.security.constants.AuthenticationConstants;

@Configuration
@PropertySource("classpath:/config/ldap/ldap_details.properties")
public class LdapConfig {

	/*
	 * @Value("dc=maxcrc,dc=com") private String baseDn;
	 * 
	 * @Bean public BaseLdapPathBeanPostProcessor ldapPathBeanPostProcessor(){
	 * BaseLdapPathBeanPostProcessor baseLdapPathBeanPostProcessor = new
	 * BaseLdapPathBeanPostProcessor();
	 * baseLdapPathBeanPostProcessor.setBasePath(baseDn); return
	 * baseLdapPathBeanPostProcessor; }
	 */

	@Autowired
	Environment env;
	
	@Bean
	public LdapContextSource contextSource() {
		LdapContextSource contextSource = new LdapContextSource();
		
		contextSource.setUrl(env.getRequiredProperty(AuthenticationConstants.LDAP_URL));
		contextSource.setBase(env.getRequiredProperty(AuthenticationConstants.LDAP_BASE));
		contextSource.setUserDn(env.getRequiredProperty(AuthenticationConstants.LDAP_USER));
		contextSource.setPassword(env.getRequiredProperty(AuthenticationConstants.LDAP_PASSWORD));
		return contextSource;
	}

	@Bean
	public LdapTemplate ldapTemplate() {
		return new LdapTemplate(contextSource());
	}
	
}
