package com.capgemini.webapp.spring.controller.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.webapp.service.api.EmailService;
import com.capgemini.webapp.service.api.SMSService;
import com.capgemini.webapp.service.api.model.EmailModel;
import com.capgemini.webapp.service.api.model.SMSModel;
/**
 * Controller handles gateway operations related to email, sms and payment
 * @author pallapat
 *
 */

@RestController
@RequestMapping("/util")
public class GatewayController {
	
	public static final Logger logger = LoggerFactory.getLogger(GatewayController.class);
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	SMSService smsService;
		
		
	@RequestMapping(value = "/email/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> sendEmail( @RequestBody  EmailModel emailBean) {		
		logger.info("GatewayController::sendEmail::Initiating email ");		
		String status = emailService.sendEmail(emailBean);		
		logger.info("GatewayController::sendEmail::Executed");		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/sms/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> sendSMS( @RequestBody  SMSModel smsBean) {
	
		logger.info("GatewayController::sendSMS::Sending SMS ");
		
		String status = smsService.sendSMS(smsBean);
		
		logger.info("GatewayController::sendSMS::Executed");
			
		return new ResponseEntity<String>(status,HttpStatus.OK);
	}
	
	
	
}
