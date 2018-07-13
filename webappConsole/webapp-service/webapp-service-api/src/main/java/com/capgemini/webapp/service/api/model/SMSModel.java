package com.capgemini.webapp.service.api.model;

import java.io.Serializable;
import java.util.List;

public class SMSModel implements Serializable{
	
	private String sender="SOCKET";
	private String route="4";
	private String country="91";
	
	private List<SMSMessageModel> sms;
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public List<SMSMessageModel> getSms() {
		return sms;
	}
	public void setSmsmessage(List<SMSMessageModel> sms) {
		this.sms = sms;
	}
	

}
