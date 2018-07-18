package com.capgemini.webapp.security.authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.codec.Base64;

public class WebAppBasicAuthenticationFilter extends BasicAuthenticationFilter {

	private AuthenticationEntryPoint authenticationEntryPoint;
	private AuthenticationManager authenticationManager;
	private RememberMeServices rememberMeServices = new NullRememberMeServices();
	private boolean ignoreFailure = false;
	private String credentialsCharset = "UTF-8";

	public WebAppBasicAuthenticationFilter(AuthenticationManager authenticationManager,
			AuthenticationEntryPoint authenticationEntryPoint) {
		super(authenticationManager, authenticationEntryPoint);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.web.authentication.www.BasicAuthenticationFilter
	 * #doFilterInternal(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Basic ")) {
			try {
				String[] failed = this.extractAndDecodeHeader(header, request);

				assert failed.length == 2;

				String username = failed[0];
				/*
				 * if (debug) { this.logger.
				 * debug("Basic Authentication Authorization header found for user \'" +
				 * username + "\'"); }
				 */

				if (this.authenticationIsRequired(username)) {
					UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,
							failed[1]);
					WebAppAuthenticationDetails details = new WebAppAuthenticationDetails(request);
					details.setTimeZone("America/New_York");
					authRequest.setDetails(details);
					Authentication authResult = this.authenticationManager.authenticate(authRequest);
					/*
					 * if (debug) { this.logger.debug("Authentication success: " + authResult); }
					 */

					SecurityContextHolder.getContext().setAuthentication(authResult);
					this.rememberMeServices.loginSuccess(request, response, authResult);
					this.onSuccessfulAuthentication(request, response, authResult);
				}
			} catch (AuthenticationException var10) {
				SecurityContextHolder.clearContext();
				/*
				 * if(debug) { this.logger.debug("Authentication request for failed: " + var10);
				 * }
				 */

				this.rememberMeServices.loginFail(request, response);
				this.onUnsuccessfulAuthentication(request, response, var10);
				if (this.ignoreFailure) {
					chain.doFilter(request, response);
				} else {
					this.authenticationEntryPoint.commence(request, response, var10);
				}
				chain.doFilter(request, response);
				return;
			}

			chain.doFilter(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {
		byte[] base64Token = header.substring(6).getBytes("UTF-8");

		byte[] decoded;
		try {
			decoded = Base64.decode(base64Token);
		} catch (IllegalArgumentException var7) {
			throw new BadCredentialsException("Failed to decode basic authentication token");
		}

		String token = new String(decoded, this.getCredentialsCharset(request));
		int delim = token.indexOf(":");
		if (delim == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		} else {
			return new String[] { token.substring(0, delim), token.substring(delim + 1) };
		}
	}

	private boolean authenticationIsRequired(String username) {
		Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
		return existingAuth != null && existingAuth.isAuthenticated()
				? (existingAuth instanceof UsernamePasswordAuthenticationToken
						&& !existingAuth.getName().equals(username) ? true
								: existingAuth instanceof AnonymousAuthenticationToken)
				: true;
	}

}
