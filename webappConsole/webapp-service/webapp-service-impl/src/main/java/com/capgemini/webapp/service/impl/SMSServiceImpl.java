package com.capgemini.webapp.service.impl;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.capgemini.webapp.common.constants.IApplicationConstants;
import com.capgemini.webapp.service.api.SMSService;
import com.capgemini.webapp.service.api.model.SMSModel;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * SMS Service implementation. Send SMS using MSG91 SMS provider POST APIs
 * 
 * @author pallapat
 *
 */
@Service("smsService")
public class SMSServiceImpl implements SMSService {
	
	@Autowired
    Environment env;

	@Override
	public String sendSMS(SMSModel smsModel) {

		String jsonInString = "";
		String status = "";
		try {
			
				URI uri  = new URI(env.getProperty(IApplicationConstants.SMS_GATEWAY_POST_URL));
				Gson gson = new Gson();
				jsonInString = gson.toJson(smsModel);
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.add(IApplicationConstants.AUTH_KEY, env.getProperty(IApplicationConstants.SMS_GATEWAY_AUTH_KEY));
				HttpEntity<String> entity = new HttpEntity<String>(jsonInString, headers);
				ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
				System.out.println(response.getStatusCode());
				if (response.getStatusCode().equals(HttpStatus.OK)) {
		
					JsonParser parse = new JsonParser();
					JsonObject jobj = (JsonObject) parse.parse(response.getBody());
					if (jobj.get(IApplicationConstants.SMS_RESPONSE_TYPE) != null) {
						String type = jobj.get(IApplicationConstants.SMS_RESPONSE_TYPE).getAsString();
						if (type != null && type.equals(new String("success"))) {
		
							status = IApplicationConstants.STATUS_SUCCESS;
		
						} else
							status = IApplicationConstants.STATUS_FAILED;
		
					}
				}//end of if

	}
	catch(URISyntaxException e1)
	{
		// TODO Auto-generated catch block
		e1.printStackTrace();

	}catch(JsonIOException e)
	{
			
			e.printStackTrace();
			
		}return status;

}
	


}
