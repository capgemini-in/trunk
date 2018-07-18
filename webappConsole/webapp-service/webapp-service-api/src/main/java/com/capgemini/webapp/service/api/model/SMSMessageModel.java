package com.capgemini.webapp.service.api.model;

import java.util.ArrayList;

public class SMSMessageModel {
	
	

	String message;
	ArrayList<String> to = new ArrayList<String>();

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList<String> getTo() {
		return to;
	}

	/*public void setNumbers(String numbers) {
		to.add(numbers);
	}
*/
	
	public void setTo(ArrayList<String> numbers) {
		to=numbers;
	}
}
