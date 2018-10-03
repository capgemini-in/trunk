package com.capgemini.webapp.service.api.model;

import java.io.Serializable;

/**
 * Model for Dealer Entity
 * 
 * @author pallapat
 *
 */
public class DealerModel  extends UserModel implements Serializable {

	private static final long serialVersionUID = 1L;

	//private int id;
	//private String code;
	private String name;
//	private String firstName;
	//private String lastName;
	//private String address;
	//private String pincode;
	private String website;
	//private String email;
	//private String mobileNumber;
	private String landline;
	private String latitude;
	private String longitude;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getLandline() {
		return landline;
	}
	public void setLandline(String landline) {
		this.landline = landline;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

		
}
