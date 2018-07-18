package com.capgemini.webapp.security.authentication;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.springframework.security.core.userdetails.UserDetails;

public class CustomLoginContext extends LoginContext {

	public CustomLoginContext(String name, CallbackHandler callbackHandler) throws LoginException {
		super(name, callbackHandler);
		// TODO Auto-generated constructor stub
	}
	
	public UserDetails isValidUser(String username, String password) {
		
		try {
			login();
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

}
