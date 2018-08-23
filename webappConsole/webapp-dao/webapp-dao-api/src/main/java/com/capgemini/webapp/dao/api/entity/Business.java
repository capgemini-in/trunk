package com.capgemini.webapp.dao.api.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "Business")
public class Business implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "business_id")
	private Integer businessId;
	
	@Column(name = "business_code")
	private String businessCode;
	
	@Column(name = "business_name")
	private String businessName;

	@Column(name = "IS_ACTIVE")
	private String isActive;
		
	/*@OneToMany(mappedBy ="businessId" )
	@JsonBackReference
	private Set<BusinessType> businessType=new HashSet<BusinessType>();		
	
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "menu_business_mapping", joinColumns = { @JoinColumn(name = "business_id") }, inverseJoinColumns = {
			@JoinColumn(name = "menu_id") })
	private Set<BusinessMenu> events = new HashSet<BusinessMenu>();*/
	
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

	/*public Set<BusinessType> getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Set<BusinessType> businessType) {
		this.businessType = businessType;
	}*/
}
