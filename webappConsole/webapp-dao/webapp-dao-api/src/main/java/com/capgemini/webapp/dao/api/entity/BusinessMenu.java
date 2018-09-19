package com.capgemini.webapp.dao.api.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "menu")
public class BusinessMenu implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menu_id")
	private Integer menuId;

	@Column(name = "menu_code")
	private String menuCode;

	@Column(name = "menu_name")
	private String menuName;

	@Column(name = "IS_ACTIVE")
	private String isActive;
	
	
	@OneToMany(mappedBy ="menuId",cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<BusinessSubMenu> subMenus=new HashSet<BusinessSubMenu>();
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "userprofile_menu_mapping", joinColumns = { @JoinColumn(name = "menu_id") }, inverseJoinColumns = {
			@JoinColumn(name = "profile_id") })	
	private Set<UserProfile> userProfile = new TreeSet<UserProfile>();
	
	/*@ManyToMany(mappedBy = "menus")
	@JsonManagedReference
    private List<BusinessType> businessTypes;	
	
	public List<BusinessType> getBusinessTypes() {
		return businessTypes;
	}

	public void setBusinessTypes(List<BusinessType> businessTypes) {
		this.businessTypes = businessTypes;
	}*/

	public Set<BusinessSubMenu> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(Set<BusinessSubMenu> subMenus) {
		this.subMenus = subMenus;
	}

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

	public Set<UserProfile> getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(Set<UserProfile> userProfile) {
		this.userProfile = userProfile;
	}
	
	

}
