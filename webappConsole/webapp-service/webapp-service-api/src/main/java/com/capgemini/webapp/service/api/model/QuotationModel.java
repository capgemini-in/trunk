package com.capgemini.webapp.service.api.model;

public class QuotationModel {
	
	private UserModel userModel;
	
	private CategoryVariantsModel varaintModel;
	
	private DealerModel dealerModel;
	
	private CityModel cityModel;
	
	double discountedPrice;
	
	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	public CategoryVariantsModel getVaraintModel() {
		return varaintModel;
	}

	public void setVaraintModel(CategoryVariantsModel varaintModel) {
		this.varaintModel = varaintModel;
	}
	
	public DealerModel getDealerModel() {
		return dealerModel;
	}

	public void setDealerModel(DealerModel dealerModel) {
		this.dealerModel = dealerModel;
	}

	public CityModel getCityModel() {
		return cityModel;
	}

	public void setCityModel(CityModel cityModel) {
		this.cityModel = cityModel;
	}

	public double getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(double discountedPrice) {
		this.discountedPrice = discountedPrice;
	}
	
	
	
	

}
