package com.capgemini.webapp.service.api.model;

import java.io.Serializable;

public class BusinessSubMenuModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private Integer subMenuId;

	private String subMenuCode;

	private String subMenuName;
	
	private String isActive;
	
	//private Set<Category> category=new HashSet<Category>();

	/*private BusinessMenu menuId;	
	
	public BusinessMenu getMenuId() {
		return menuId;
	}

	public void setMenuId(BusinessMenu menuId) {
		this.menuId = menuId;
	}*/

	public Integer getSubMenuId() {
		return subMenuId;
	}

	public void setSubMenuId(Integer subMenuId) {
		this.subMenuId = subMenuId;
	}

	public String getSubMenuCode() {
		return subMenuCode;
	}

	public void setSubMenuCode(String subMenuCode) {
		this.subMenuCode = subMenuCode;
	}

	public String getSubMenuName() {
		return subMenuName;
	}

	public void setSubMenuName(String subMenuName) {
		this.subMenuName = subMenuName;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	/*public Set<Category> getCategory() {
		return category;
	}

	public void setCategory(Set<Category> category) {
		this.category = category;
	}*/
}
