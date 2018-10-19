package com.capgemini.webapp.security.authentication;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.security.auth.login.LoginContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.capgemini.webapp.security.config.EncodeDecoder;

@Component

public class CustomDBAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	@Qualifier("customUserDetailsService")
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/*@Autowired
	DefaultJaasAuthenticationProvider dbAuthenticationProvider;*/
	
	@Autowired
	@Qualifier("roleGranter")
	AuthorityGranter roleGranter;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName().toString();
		String password = (String) authentication.getCredentials().toString();
		Authentication auth = null;
		//CustomUserDetailsService userDetailService = new CustomUserDetailsService();
		LoginContext lc;
		try {
			
			System.setProperty("java.security.auth.login.config", "D:\\AssetCreation\\GitRepo\\webappConsole\\webapp-security\\src\\main\\resources\\jaas.config");
			lc = new LoginContext("assetLogin", new JaasCallbackHandler(username, password));
			lc.login();
			Set<String> roles = null;
			List<GrantedAuthority> authorities = new ArrayList<>();
			for(Principal principal : lc.getSubject().getPrincipals() ) {
				
				roles = roleGranter.grant(principal);
				for(String role : roles) {
					
					authorities.add(new SimpleGrantedAuthority(role));
				}
			}
			if (authorities.size() > 0) {
				auth =  new UsernamePasswordAuthenticationToken(lc.getSubject().getPrincipals(), password, authorities);
			} else {
				throw new BadCredentialsException(" Password is Wrong");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BadCredentialsException(" Password is Wrong");
		}
			
		return auth;
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}

}
