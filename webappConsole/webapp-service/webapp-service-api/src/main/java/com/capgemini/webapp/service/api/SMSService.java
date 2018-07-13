package com.capgemini.webapp.service.api;


import com.capgemini.webapp.service.api.model.SMSModel;
/**
 * Interface SMSService
 * @author pallapat
 *
 */
public interface SMSService {
	public String sendSMS(SMSModel smsModel);
}
