package com.capgemini.webapp.dao.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "variants_details")
public class VariantDetails implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	/*@Id
	@Column(name = "variant_id")
	private int variantId;

	@Column(name = "fuel_type")
	private String fuelType;*/
	
	@EmbeddedId
	private VariantDetailsID variantDetailsId;
	
	@ManyToOne
    @JoinColumn(name = "variant_id",insertable = false, updatable = false)
    private CategoryVariants variant;
	
	@Column(name = "showroom_price")
	private String showroomPrice;

	@Column(name = "insurance_price")
	private String insruancePrice;
	
	@Column(name = "registration_charges")
	private String regCharges;
	
	@Column(name = "onroad_price")
	private String onRoadPrice;
	
	@Column(name = "downpayment")
	private String downPayment;

		
	public CategoryVariants getVariant() {
		return variant;
	}

	public void setVariant(CategoryVariants variant) {
		this.variant = variant;
	}

	public VariantDetailsID getVariantDetailsId() {
		return variantDetailsId;
	}

	public void setVariantDetailsId(VariantDetailsID variantDetailsId) {
		this.variantDetailsId = variantDetailsId;
	}

	/*
	public CategoryVariants getVariantId() {
		return variantId;
	}

	public void setVariantId(CategoryVariants variantId) {
		this.variantId = variantId;
	}
*/
	/*public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}*/

	public String getShowroomPrice() {
		return showroomPrice;
	}

	public void setShowroomPrice(String showroomPrice) {
		this.showroomPrice = showroomPrice;
	}

	public String getInsruancePrice() {
		return insruancePrice;
	}

	public void setInsruancePrice(String insruancePrice) {
		this.insruancePrice = insruancePrice;
	}

	public String getRegCharges() {
		return regCharges;
	}

	public void setRegCharges(String regCharges) {
		this.regCharges = regCharges;
	}

	public String getOnRoadPrice() {
		return onRoadPrice;
	}

	public void setOnRoadPrice(String onRoadPrice) {
		this.onRoadPrice = onRoadPrice;
	}

	public String getDownPayment() {
		return downPayment;
	}

	public void setDownPayment(String downPayment) {
		this.downPayment = downPayment;
	}
			
	
}
