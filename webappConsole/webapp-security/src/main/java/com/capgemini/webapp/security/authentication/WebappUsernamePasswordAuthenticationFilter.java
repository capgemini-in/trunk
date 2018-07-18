/**
 * 
 */
package com.capgemini.webapp.security.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author awarhoka
 *
 */
public class WebappUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter#attemptAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		return super.attemptAuthentication(request, response);
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter#setDetails(javax.servlet.http.HttpServletRequest, org.springframework.security.authentication.UsernamePasswordAuthenticationToken)
	 */
	@Override
	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		
		WebAppAuthenticationDetails details = new WebAppAuthenticationDetails(request);
		authRequest.setDetails(details);
	}

}
