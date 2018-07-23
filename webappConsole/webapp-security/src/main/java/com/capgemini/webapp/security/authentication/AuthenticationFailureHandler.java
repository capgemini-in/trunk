package com.capgemini.webapp.security.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		String errorType = "login.invalid.text";
		if (exception.getClass().isAssignableFrom(LockedException.class)) {
			errorType = "login.account.locked.text";
		} else if (exception.getClass().isAssignableFrom(DisabledException.class)) {
			errorType = "login.account.inactive.text";
		}
		if (isAllowSessionCreation()) {
			request.getSession().setAttribute("SPRING_SECURITY_LAST_USERNAME", "userName");
			request.getSession().setAttribute("error_msg", errorType);
		}
		setDefaultFailureUrl("/login?error");
		super.onAuthenticationFailure(request, response, exception);
	}

}
