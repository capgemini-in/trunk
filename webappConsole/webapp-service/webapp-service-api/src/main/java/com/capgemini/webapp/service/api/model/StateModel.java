package com.capgemini.webapp.service.api.model;

import java.io.Serializable;

import com.capgemini.webapp.dao.api.entity.Country;

public class StateModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer stateId;
	
	private String stateCode;
	
	private String stateName;

	private String isActive;
	
	private Country coun;

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

	public Country getCoun() {
		return coun;
	}

	public void setCoun(Country coun) {
		this.coun = coun;
	}

	
	
	
}
