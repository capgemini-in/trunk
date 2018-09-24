package com.capgemini.webapp.service.api.model;

import java.io.Serializable;

public class QuotationModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int quotation_id;
	
	private UserModel user;
	
	private CategoryVariantsModel variant;
	
	private DealerModel dealer;
	
	private CityModel city;
	
	double discountedPrice;
	
	public int getQuotation_id() {
		return quotation_id;
	}

	public void setQuotation_id(int quotation_id) {
		this.quotation_id = quotation_id;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public CategoryVariantsModel getVariant() {
		return variant;
	}

	public void setVariant(CategoryVariantsModel variant) {
		this.variant = variant;
	}

	public DealerModel getDealer() {
		return dealer;
	}

	public void setDealer(DealerModel dealer) {
		this.dealer = dealer;
	}

	public CityModel getCity() {
		return city;
	}

	public void setCity(CityModel city) {
		this.city = city;
	}

	public double getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(double discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	
	
}