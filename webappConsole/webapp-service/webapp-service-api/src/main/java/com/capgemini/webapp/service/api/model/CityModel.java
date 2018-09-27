package com.capgemini.webapp.service.api.model;

import java.io.Serializable;

public class CityModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer cityId;
	
	private String cityCode;
	
	private String cityName;

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
