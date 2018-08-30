package com.capgemini.webapp.dao.api.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * State entity class
 * @author pallapat
 *
 */
@Entity
@Table(name = "states")
public class State implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "state_id")
	private Integer stateId;
	
	@Column(name = "state_code")
	private String stateCode;
	
	@Column(name = "state_name")
	private String stateName;

	@Column(name = "IS_ACTIVE")
	private String isActive;
	
	@ManyToOne
    @JoinColumn(name="country_id")
	//@JsonManagedReference
	private Country coun;
	
	
	@OneToMany(mappedBy ="cityState" )
	//@JsonBackReference
	private Set<City> city=new HashSet<City>();

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Country getCoun() {
		return coun;
	}

	public void setCoun(Country coun) {
		this.coun = coun;
	}

	public Set<City> getCity() {
		return city;
	}

	public void setCity(Set<City> city) {
		this.city = city;
	}

	
	
}
