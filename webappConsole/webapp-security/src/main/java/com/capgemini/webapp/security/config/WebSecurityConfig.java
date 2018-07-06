package com.capgemini.webapp.security.config;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.AppConfigurationEntry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.authentication.jaas.DefaultJaasAuthenticationProvider;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.capgemini.webapp.security.constants.AuthenticationConstants;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource("classpath:/config/ldap/ldap_details.properties")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;
	
	@Autowired
	@Qualifier("roleGranter")
	AuthorityGranter roleGranter;
	
	@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	PersistentTokenRepository tokenRepository;

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		String authentication = env.getRequiredProperty(AuthenticationConstants.AUTHENTICATION);
		if(authentication.equalsIgnoreCase(AuthenticationConstants.LDAP_AUTH)) {
			
			auth.authenticationProvider(ldapAuthenticationProvider());
		}
		if(authentication.equalsIgnoreCase(AuthenticationConstants.DB_AUTH)) {
			
			auth.userDetailsService(userDetailsService);
			auth.authenticationProvider(dbAuthenticationProvider());
		}
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/list","/home","/userpermission","/usermenuright","/editProduct")
				.access("hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')")
				.antMatchers("/newuser/**", "/delete-user-*").access("hasRole('ADMIN')").antMatchers("/edit-user-*")
				.access("hasRole('ADMIN') or hasRole('DBA')").and().formLogin().loginPage("/login")
				.loginProcessingUrl("/login").usernameParameter("ssoId").passwordParameter("password").and()
				.rememberMe().rememberMeParameter("remember-me").tokenRepository(tokenRepository)
				.tokenValiditySeconds(86400).and().exceptionHandling().accessDeniedPage("/Access_Denied").and().csrf().disable() ;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}*/
	
	@Bean
	public DefaultJaasAuthenticationProvider dbAuthenticationProvider() {
		DefaultJaasAuthenticationProvider authenticationProvider = new DefaultJaasAuthenticationProvider();
		
		Map<String, String> options = new HashMap<String, String>();
		options.put(AuthenticationConstants.DEBUG, env.getRequiredProperty(AuthenticationConstants.DEBUG));		
		
		AppConfigurationEntry appConfigEntries[] = new AppConfigurationEntry[1];
		appConfigEntries[0] = new AppConfigurationEntry("com.capgemini.pocwebapp.spring.config.security.Login", javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, options);
      
		Map<String, AppConfigurationEntry[]> mappedConfigurations = new HashMap();
		mappedConfigurations.put(AuthenticationConstants.APP_CONFIG_ENTRY_KEY, appConfigEntries);
		authenticationProvider.setConfiguration(new InMemoryConfiguration(mappedConfigurations));
		
		AuthorityGranter[] authorityGranter = new AuthorityGranter[1];
		authorityGranter[0] = roleGranter;		
		authenticationProvider.setAuthorityGranters(authorityGranter);
		
		return authenticationProvider;
	}
	
	@Bean
	public DefaultJaasAuthenticationProvider ldapAuthenticationProvider() {
		DefaultJaasAuthenticationProvider authenticationProvider = new DefaultJaasAuthenticationProvider();
		
	/*	Map<String, String> options = new HashMap<String, String>();
		options.put(AuthenticationConstants.DEBUG, env.getRequiredProperty(AuthenticationConstants.DEBUG));*/
		
		Map<String, String> options = new HashMap<String, String>();
        options.put(AuthenticationConstants.USER_PROVIDER, env.getRequiredProperty(AuthenticationConstants.USER_PROVIDER));
        options.put(AuthenticationConstants.USER_FILTER, env.getRequiredProperty(AuthenticationConstants.USER_FILTER));
        options.put(AuthenticationConstants.USE_SSL, env.getRequiredProperty(AuthenticationConstants.USE_SSL));
        //options.put("authIdentity", "uid={USERNAME}");
        options.put(AuthenticationConstants.DEBUG, env.getRequiredProperty(AuthenticationConstants.DEBUG));
		
		AppConfigurationEntry appConfigEntries[] = new AppConfigurationEntry[1];
        appConfigEntries[0] = new AppConfigurationEntry(env.getRequiredProperty(AuthenticationConstants.LDAP_LOGIN_MODULE), javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, options);
		//appConfigEntries[0] = new AppConfigurationEntry("com.capgemini.pocwebapp.spring.config.security.Login", javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, options);
      
		Map<String, AppConfigurationEntry[]> mappedConfigurations = new HashMap();
		mappedConfigurations.put(AuthenticationConstants.APP_CONFIG_ENTRY_KEY, appConfigEntries);
		authenticationProvider.setConfiguration(new InMemoryConfiguration(mappedConfigurations));
		
		AuthorityGranter[] authorityGranter = new AuthorityGranter[1];
		authorityGranter[0] = roleGranter;
		
		authenticationProvider.setAuthorityGranters(authorityGranter);
		return authenticationProvider;
	}

	@Bean
	public PersistentTokenBasedRememberMeServices getPersistentTokenBasedRememberMeServices() {
		PersistentTokenBasedRememberMeServices tokenBasedservice = new PersistentTokenBasedRememberMeServices(
				"remember-me", userDetailsService, tokenRepository);
		return tokenBasedservice;
	}

	@Bean
	public AuthenticationTrustResolver getAuthenticationTrustResolver() {
		return new AuthenticationTrustResolverImpl();
	}
    public static void main(String[] args) {
    	PasswordEncoder encoder = new BCryptPasswordEncoder();    
    	boolean value = encoder.matches("admin",encoder.encode("admin")); 
    }
}
