package com.capgemini.webapp.service.api.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class CategoryModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer categoryId;
	private String categoryCode;
	private String categoryName;
	private String imagePath;
	
	//private BusinessSubMenuModel subMenuId;
	
	private Set<CategoryVariantsModel> variants = new HashSet<CategoryVariantsModel>();
	
	
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public Set<CategoryVariantsModel> getVariants() {
		return variants;
	}
	public void setVariants(Set<CategoryVariantsModel> variants) {
		this.variants = variants;
	}
	
	/*public int getSubMenuId() {
		return subMenuId;
	}
	public void setSubMenuId(int subMenuId) {
		this.subMenuId = subMenuId;
	}*/
	
	

}
