/**
 * 
 */
package com.capgemini.webapp.security.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.capgemini.webapp.service.api.model.UserModel;

/**
 * @author awarhoka
 *
 */
@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.
	 * AbstractAuthenticationTargetUrlRequestHandler#handle(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * org.springframework.security.core.Authentication)
	 */
	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		Integer idleTimeout = 3000;
		Integer messageDisplayTime = 300;
		request.getSession().setMaxInactiveInterval(idleTimeout + messageDisplayTime);
		request.getSession().setAttribute("showAlert", "true");
		recordLoginSuccess(authentication);

		if (response.isCommitted()) {
			return;
		}

		String targetUrl = determineTargetUrl(authentication);
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	/**
	 * Record the last login date for the user
	 *
	 * @param authentication
	 *            The principal of the user logged in
	 * @throws Exception
	 *             If an error occurs recording the success.
	 */

	protected void recordLoginSuccess(Authentication authentication) {

		if (authentication != null && authentication.isAuthenticated()) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			try {
				// accountEntity.setLastLogin(currentDateTimeService.getCurrentDateAndTime());
				// userDetailsService.save(accountEntity, AccountEntity.class);
			} catch (Exception e) {
			}
			resetLoginFailure(userDetails);
		}
	}

	/**
	 * Reset the user login failure count to 0.
	 * 
	 * @param user
	 */
	protected void resetLoginFailure(UserDetails userDetails) {
		if (userDetails != null) {
			try {
				// userDetailsService.save(failedLoginEntity);
			} catch (Exception e) {
				// log.error("Exception resetting failed login for account" +
				// user.getUsername(), e);
			}
		}
	}

	/*
	 * This method extracts the roles of currently logged-in user and returns
	 * appropriate URL according to his/her role.
	 */
	protected String determineTargetUrl(Authentication authentication) {
		// TODO: Bring people back to where they left off?
		return "/";
	}

	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}
}
