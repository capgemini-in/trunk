package com.capgemini.webapp.dao.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "submenu")
public class BusinessSubMenu implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sub_menu_id")
	private Integer subMenuId;

	@Column(name = "sub_menu_code")
	private String subMenuCode;

	@Column(name = "sub_menu_name")
	private String subMenuName;

	@Column(name = "IS_ACTIVE")
	private String isActive;
	
	@ManyToOne
    @JoinColumn(name="menu_id")
	@JsonBackReference
	private BusinessMenu menuId;	
	

	public BusinessMenu getMenuId() {
		return menuId;
	}

	public void setMenuId(BusinessMenu menuId) {
		this.menuId = menuId;
	}

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
	
}