package com.capgemini.webapp.security.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.capgemini.webapp.service.api.model.UserModel;
@Component
public class WebAppLogoutHandler implements LogoutHandler {

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		recordUserLogout(authentication);
	}
	
	 /**
	   * Record when the user logged out
	   *
	   * @param authentication
	   */
	  protected void recordUserLogout(Authentication authentication) {

	    if (authentication != null && authentication.isAuthenticated()) {
	      UserDetails user = (UserDetails) authentication.getPrincipal();
	      authentication.getDetails();
	      try {
	        //accountEntity.setLastLogout(currentDateTimeService.getCurrentDateAndTime());
	        //accountService.save(accountEntity, AccountEntity.class);
	      } catch (Exception e) {
/*	        if (log.isWarnEnabled())
	          log.warn("Exception saving last out date", e);*/
	      }
	    }
	  }

}
