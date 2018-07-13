package com.capgemini.webapp.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.capgemini.webapp.service.api.SMSService;
import com.capgemini.webapp.service.api.model.SMSMessageModel;
import com.capgemini.webapp.service.api.model.SMSModel;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
/**
 * SMS Service implementation. Send SMS using MSG91 SMS provider POST APIs
 * @author pallapat
 *
 */
@Service("smsService")
public class SMSServiceImpl implements SMSService {

	@Override
	public String sendSMS(SMSModel smsModel) {
		URI uri=null;
		String jsonInString ="";
		try {
			
			uri = new URI("http://api.msg91.com/api/v2/sendsms");
			Gson gson = new Gson();		
			jsonInString = gson.toJson(smsModel);
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("authkey","225748AtdcoJiJ5BN85b472938");
			HttpEntity<String> entity = new HttpEntity<String>(jsonInString,headers);
			ResponseEntity<String> response = restTemplate
		            .exchange(uri, HttpMethod.POST, entity, String.class);
			System.out.println(response.getStatusCode());
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		} catch (JsonIOException  e) {
			
			e.printStackTrace();
			
		}
			   
	    return null;
	}
	
	 public static void main(String[] args) {
			SMSServiceImpl impl=new SMSServiceImpl();
			SMSModel model=new SMSModel();
			model.setCountry("91");
			model.setRoute("4");
			
			model.setSender("TestAsset");
			SMSMessageModel msg=new SMSMessageModel();
			msg.setMessage("hello");
			msg.setNumbers("919987074418");
			ArrayList<SMSMessageModel> arr=new ArrayList();
			arr.add(msg);
			model.setSmsmessage(arr);
			impl.sendSMS(model);
					
			}
			

	
}
