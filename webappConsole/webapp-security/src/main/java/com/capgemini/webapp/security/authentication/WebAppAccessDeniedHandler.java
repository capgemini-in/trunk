/**
 * 
 */
package com.capgemini.webapp.security.authentication;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.stereotype.Component;

/**
 * @author awarhoka
 *
 */
@Component
public class WebAppAccessDeniedHandler extends AccessDeniedHandlerImpl {
	public static final String DEFAULT_ACCESS_DENIED_PATH = "/Access_Denied";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.web.access.AccessDeniedHandlerImpl#handle(javax.
	 * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * org.springframework.security.access.AccessDeniedException)
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		if (!response.isCommitted()) {

			if (accessDeniedException instanceof MissingCsrfTokenException
					|| accessDeniedException instanceof InvalidCsrfTokenException) {

				if (request.getRequestURI().contains("login")) {
					response.sendRedirect(request.getContextPath() + "/login");
				}

			} else {

				String page = DEFAULT_ACCESS_DENIED_PATH;

				if (page != null) {
					request.setAttribute("SPRING_SECURITY_403_EXCEPTION", accessDeniedException);
					response.setStatus(403);
					request.setAttribute("exception", accessDeniedException);
					RequestDispatcher dispatcher = request.getRequestDispatcher(page);
					dispatcher.forward(request, response);
				} else {
					response.sendError(403, accessDeniedException.getMessage());
				}
			}
		}
	}

}
