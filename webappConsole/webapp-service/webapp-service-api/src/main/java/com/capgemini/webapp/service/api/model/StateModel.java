package com.capgemini.webapp.service.api.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class StateModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer stateId;
	
	private String stateCode;
	
	private String stateName;

	private String isActive;
	
 	private Set<CityModel> city=new HashSet<CityModel>();

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Set<CityModel> getCity() {
		return city;
	}

	public void setCity(Set<CityModel> city) {
		this.city = city;
	}

	
	
	
	
}
