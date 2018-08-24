package com.capgemini.webapp.dao.api.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;



@Entity
@Table(name = "business_type")
public class BusinessType implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bus_type_id")
	private Integer busTypeId;
	
	@Column(name = "bus_type_code")
	private String busTypeCode;
	
	@Column(name = "bus_type_name")
	private String busTypeName;

	@Column(name = "IS_ACTIVE")
	private String isActive;
	
	@OneToOne
    @JoinColumn(name="business_id")
	@JsonManagedReference
	private Business businessId;
	
	@NotEmpty
	@ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "MENU_BUSINESS_MAPPING", joinColumns = { @JoinColumn(name = "bus_type_id") }, inverseJoinColumns = {
			@JoinColumn(name = "menu_id") })

	@JsonBackReference	
	private Set<BusinessMenu> menus = new HashSet<BusinessMenu>();
		
		
	public Business getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Business businessId) {
		this.businessId = businessId;
	}

	public Set<BusinessMenu> getMenus() {
		return menus;
	}

	public void setMenus(Set<BusinessMenu> menus) {
		this.menus = menus;
	}

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
	
	
}
