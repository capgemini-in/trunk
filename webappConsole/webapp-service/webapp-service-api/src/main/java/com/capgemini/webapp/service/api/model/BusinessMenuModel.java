package com.capgemini.webapp.service.api.model;

import java.io.Serializable;

public class BusinessMenuModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer businessId;
	private String businessCode;
	private String businessName;
	private String isActive;

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}
