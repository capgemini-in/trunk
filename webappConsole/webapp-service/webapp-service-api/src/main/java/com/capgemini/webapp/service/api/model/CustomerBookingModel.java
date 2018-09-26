package com.capgemini.webapp.service.api.model;

import java.io.Serializable;

public class CustomerBookingModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int booking_id;
	
	private UserModel user;
	
	private QuotationModel quotation;
	
	private CategoryVariantsModel variant;
	
	private DealerModel dealer;
	
	private String exchangeVehicle;
	
	private String paymentOption;
	
	private double finalPrice;
	
	private String status;
	
	private String transactionId;

	public int getBooking_id() {
		return booking_id;
	}

	public void setBooking_id(int booking_id) {
		this.booking_id = booking_id;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public QuotationModel getQuotation() {
		return quotation;
	}

	public void setQuotation(QuotationModel quotation) {
		this.quotation = quotation;
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

	
	public String getExchangeVehicle() {
		return exchangeVehicle;
	}

	public void setExchangeVehicle(String exchangeVehicle) {
		this.exchangeVehicle = exchangeVehicle;
	}

	public String getPaymentOption() {
		return paymentOption;
	}

	public void setPaymentOption(String paymentOption) {
		this.paymentOption = paymentOption;
	}

	public double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	
	
}