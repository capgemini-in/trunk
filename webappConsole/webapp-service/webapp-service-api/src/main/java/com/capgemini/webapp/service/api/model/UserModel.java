package com.capgemini.webapp.service.api.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String ssoId;

	@JsonIgnore
	private String password;

	private String firstName;

	private String lastName;

	private String email;
	
	private String mobileNumber;
	
	private String address;
	
	private String zipcode;
	
	/*private String state;
	
	private String city;
	
	private String country;*/
	
	private CityModel city;
	

	private Set<UserProfileModel> userProfiles = new HashSet<UserProfileModel>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSsoId() {
		return ssoId;
	}

	public void setSsoId(String ssoId) {
		this.ssoId = ssoId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<UserProfileModel> getUserProfiles() {
		return userProfiles;
	}

	public void setUserProfiles(Set<UserProfileModel> userProfileModels) {
		this.userProfiles = userProfileModels;
	}
	
	

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

/*	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}*/
	
	public CityModel getCity() {
		return city;
	}

	public void setCity(CityModel city) {
		this.city = city;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ssoId == null) ? 0 : ssoId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UserModel))
			return false;
		UserModel other = (UserModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ssoId == null) {
			if (other.ssoId != null)
				return false;
		} else if (!ssoId.equals(other.ssoId))
			return false;
		return true;
	}

	/*
	 * DO-NOT-INCLUDE passwords in toString function. It is done here just for
	 * convenience purpose.
	 */
	@Override
	public String toString() {
		return "UserModel [id=" + id + ", ssoId=" + ssoId + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + "]";
	}

}
