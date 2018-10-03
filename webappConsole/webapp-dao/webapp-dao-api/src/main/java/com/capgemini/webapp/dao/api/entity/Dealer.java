package com.capgemini.webapp.dao.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="dealer2")
@PrimaryKeyJoinColumn(name = "dealer_id")
/**
 * Dealer Entity Class
 * @author pallapat
 *
 */
public class Dealer  extends User implements Serializable  {

	private static final long serialVersionUID = 1L;
		
	@Column(name = "website")
	private String website;
	
	@Column(name = "landline_Num")
	private String landline;
	
	@Column(name = "latitude")
	private String latitude;
	
	@Column(name = "longitude")
	private String longitude;

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLandline() {
		return landline;
	}

	public void setLandline(String landline) {
		this.landline = landline;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
		
}
