package com.capgemini.webapp.common.utils;
import com.google.gson.JsonObject;

public enum ServicesEnumMessages {

	  OK("OK"),
	  CREDENTIAL_ERROR("Invalid Credentials"),
	  MISSING_ACCOUNT_PARAMS("ssoID, password  are required fields");

	  private String message;	  

	  ServicesEnumMessages(String message) {
	    this.message = message;
	  }

	  public String getMessage() {
	    return message;
	  }

	  public void setMessage(String message) {
	    this.message = message;
	  }

	  public JsonObject getJsonMessage() {
	    JsonObject msg = new JsonObject();
	    msg.addProperty("message", message);
	    return msg;
	  }

	}
