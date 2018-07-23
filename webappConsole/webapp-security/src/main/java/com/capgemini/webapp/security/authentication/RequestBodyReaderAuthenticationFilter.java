package com.capgemini.webapp.security.authentication;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.capgemini.webapp.security.constants.AuthenticationConstants;

public class RequestBodyReaderAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private static final Log LOG = LogFactory.getLog(RequestBodyReaderAuthenticationFilter.class);
	private static final String ERROR_MESSAGE = "Something went wrong while parsing /login request body";
	//private final ObjectMapper objectMapper = new ObjectMapper();

	public RequestBodyReaderAuthenticationFilter() {
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String requestBody;
		try {
			requestBody = IOUtils.toString(request.getReader());
			String ssoId = null;
			String password = null;
			StringTokenizer stringToken = new StringTokenizer(requestBody, "&");
			while (stringToken.hasMoreElements()) {
				String credentials = (String) stringToken.nextElement();
				String[] array = credentials.split("=");
				if (array != null && array.length != 0) {
					if (AuthenticationConstants.SSOID.equalsIgnoreCase(array[0])) {
						ssoId = array[1];
					}
					if (AuthenticationConstants.PASSWORD.equalsIgnoreCase(array[0])) {
						password = array[1];
					}
				}

			}
			// LoginRequest authRequest = objectMapper.readValue(requestBody,
			// LoginRequest.class);

			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(ssoId, password);

			// Allow subclasses to set the "details" property
			// setDetails(request, token);
			setRequestAndToken(request, token);
			return getAuthenticationObject(token);

		} catch (IOException e) {
			LOG.error(ERROR_MESSAGE, e);
			throw new InternalAuthenticationServiceException(ERROR_MESSAGE, e);
		}
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	public Authentication getAuthenticationObject(UsernamePasswordAuthenticationToken token) {
		return this.getAuthenticationManager().authenticate(token);
	}

	/**
	 * 
	 * @param request
	 * @param token
	 */
	public void setRequestAndToken(HttpServletRequest request, UsernamePasswordAuthenticationToken token) {
		setDetails(request, token);
	}
}
