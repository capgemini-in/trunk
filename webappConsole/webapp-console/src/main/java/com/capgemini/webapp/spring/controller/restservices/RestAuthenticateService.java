package com.capgemini.webapp.spring.controller.restservices;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.webapp.common.constants.IApplicationConstants;
import com.capgemini.webapp.common.utils.ServicesEnumMessages;
import com.capgemini.webapp.security.authentication.RequestBodyReaderAuthenticationFilter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping("restservices")
public class RestAuthenticateService {	

	@Autowired
	private RequestBodyReaderAuthenticationFilter authenticationFilter;

	private Log logger = LogFactory.getLog(RestAuthenticateService.class.getName());

	@RequestMapping(value = "/validateAccount", headers = "Accept=*/*", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> authenticate(HttpServletRequest request, HttpServletResponse response) {
		
		logger.info("::RestAuthenticateService : authenticate() Called to Authenticate the User::");
		JsonObject message = new JsonObject();
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {
			JsonObject reqJson = new JsonParser().parse(request.getReader()).getAsJsonObject();
			String ssoId = reqJson.get(IApplicationConstants.LOGIN_FORM_USERNAME_FIELD).getAsString();
			String password = reqJson.get(IApplicationConstants.LOGIN_FORM_PASSWORD_FIELD).getAsString();
			if (ssoId != null && password != null) {
				logger.info(":: Authenticating for User id ::"+ssoId);
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(ssoId, password);
				authenticationFilter.setRequestAndToken(request, token);
				Authentication auth = authenticationFilter.getAuthenticationObject(token);
				Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

				JsonObject jsonObj = new JsonObject();
				if (auth.isAuthenticated()) {
					logger.info("::User ::" +ssoId+ " Authenticated ..");
					jsonObj = ServicesEnumMessages.OK.getJsonMessage();
					for (GrantedAuthority role : authorities) {
						jsonObj.addProperty("userType", role.getAuthority());
					}
					jsonObj.addProperty("uid", ssoId);
					jsonObj.addProperty("status", 200);
				}
				message = auth.isAuthenticated() ? jsonObj.getAsJsonObject()
						: ServicesEnumMessages.CREDENTIAL_ERROR.getJsonMessage();
			} else {
				message = ServicesEnumMessages.MISSING_ACCOUNT_PARAMS.getJsonMessage();
			}
		} catch (Exception ex) {
			message.addProperty("message", ex.toString());
			message.addProperty("status", 400);
			logger.error("RestAccountService ->authenticate", ex);
		}
		return new ResponseEntity<String>(message.toString(), httpHeaders, HttpStatus.OK);
	}
}
