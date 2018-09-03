package com.capgemini.webapp.service.api.model;

import java.io.Serializable;

import com.capgemini.webapp.dao.api.entity.CategoryVariants;
import com.capgemini.webapp.dao.api.entity.VariantDetailsID;

public class VariantDetailsModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private VariantDetailsID variantDetailsId;

	private String showroomPrice;

	private String insruancePrice;

	private String regCharges;

	private String onRoadPrice;

	private String downPayment;
	
	private CategoryVariants variant;
	

	public CategoryVariants getVariant() {
		return variant;
	}

	public void setVariant(CategoryVariants variant) {
		this.variant = variant;
	}

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

	public VariantDetailsID getVariantDetailsId() {
		return variantDetailsId;
	}

	public void setVariantDetailsId(VariantDetailsID variantDetailsId) {
		this.variantDetailsId = variantDetailsId;
	}

	
}
