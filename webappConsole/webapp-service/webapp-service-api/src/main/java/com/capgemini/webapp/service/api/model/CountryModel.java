package com.capgemini.webapp.service.api.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class CountryModel implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	private Integer countryId;
	
	
	private String countryCode;
	
	
	private String countryName;


	private String isActive;
	
	
   	private Set<StateModel> state=new HashSet<StateModel>();


	public Integer getCountryId() {
		return countryId;
	}


	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}


	public String getCountryCode() {
		return countryCode;
	}


	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}


	public String getCountryName() {
		return countryName;
	}


	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}


	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}


	public Set<StateModel> getState() {
		return state;
	}


	public void setState(Set<StateModel> state) {
		this.state = state;
	}
	
   	
}
