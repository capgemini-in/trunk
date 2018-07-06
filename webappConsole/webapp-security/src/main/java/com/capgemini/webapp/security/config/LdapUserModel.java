package com.capgemini.webapp.security.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.capgemini.webapp.service.api.model.UserProfileModel;

@Component
public class LdapUserModel {
	
	private String uid;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<UserProfileModel> userProfile = new HashSet<>();
    
    
    public LdapUserModel() {
    }

    public LdapUserModel(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public LdapUserModel(String uid, String fullName, String lastName) {
        this.uid = uid;
        this.firstName = fullName;
        this.lastName = lastName;
    }

    
    
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<UserProfileModel> getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(Set<UserProfileModel> userProfile) {
		this.userProfile = userProfile;
	}

	@Override
	    public String toString() {
	        return "LdapUser{" +
	                "fullName='" + firstName + '\'' +
	                ", lastName='" + lastName + '\'' +
	                ", Email ='" + email + '\'' +
	                '}';
	    }
}
