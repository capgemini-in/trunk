package com.capgemini.webapp.service.api.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.capgemini.webapp.dao.api.entity.BusinessMenu;
import com.capgemini.webapp.dao.api.entity.BusinessSubMenu;

public class BusinessTypeModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer busTypeId;
	
	private String busTypeCode;
	
	private String busTypeName;

	private String isActive;
		
	private Set<BusinessMenu> menus = new HashSet<BusinessMenu>();
	
	public Integer getBusTypeId() {
		return busTypeId;
	}

	public void setBusTypeId(Integer busTypeId) {
		this.busTypeId = busTypeId;
	}

	public String getBusTypeCode() {
		return busTypeCode;
	}

	public void setBusTypeCode(String busTypeCode) {
		this.busTypeCode = busTypeCode;
	}

	public String getBusTypeName() {
		return busTypeName;
	}

	public void setBusTypeName(String busTypeName) {
		this.busTypeName = busTypeName;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Set<BusinessMenu> getMenus() {
		return menus;
	}

	public void setMenus(Set<BusinessMenu> menus) {
		this.menus = menus;
	}
}
