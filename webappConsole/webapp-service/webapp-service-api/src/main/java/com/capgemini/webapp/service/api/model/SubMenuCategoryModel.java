package com.capgemini.webapp.service.api.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class SubMenuCategoryModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*private Integer subMenuId;

	private String subMenuCode;

	private String subMenuName;
	
	private String isActive;*/	

	private Set<CategoryModel> category=new HashSet<CategoryModel>();
	
	
/*	public Integer getSubMenuId() {
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
	}*/
	
	public Set<CategoryModel> getCategory() {
		return category;
	}
	public void setCategory(Set<CategoryModel> category) {
		this.category = category;
	}
	
	

}
