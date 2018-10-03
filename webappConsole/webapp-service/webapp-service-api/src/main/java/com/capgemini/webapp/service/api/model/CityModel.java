package com.capgemini.webapp.service.api.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CityModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer cityId;
	
	@JsonIgnore
	private String cityCode;
	
	private String cityName;

	@JsonIgnore
	private String isActive;

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	
	
}
