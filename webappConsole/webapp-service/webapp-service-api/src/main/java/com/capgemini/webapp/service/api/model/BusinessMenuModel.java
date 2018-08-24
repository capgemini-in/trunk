package com.capgemini.webapp.service.api.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class BusinessMenuModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer menuId;
	
	private String menuCode;
	
	private String menuName;
	
	private String isActive;
	
	private Set<BusinessSubMenuModel> subMenus=new HashSet<BusinessSubMenuModel>();

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Set<BusinessSubMenuModel> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(Set<BusinessSubMenuModel> subMenus) {
		this.subMenus = subMenus;
	}
}
