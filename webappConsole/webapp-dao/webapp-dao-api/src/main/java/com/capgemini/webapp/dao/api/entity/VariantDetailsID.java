package com.capgemini.webapp.dao.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VariantDetailsID implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name="variant_id")
	private int variant;

	@Column(name = "fuel_type")
	private String fuelType;

	
	public int getVariant() {
		return variant;
	}

	public void setVariant(int variant) {
		this.variant = variant;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

}
