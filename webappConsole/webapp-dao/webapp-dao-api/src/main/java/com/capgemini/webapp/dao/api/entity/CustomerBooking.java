package com.capgemini.webapp.dao.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="booking_request")
public class CustomerBooking  implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "booking_id")
	private int booking_id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToOne
	@JoinColumn(name="quot_id")
	private Quotation quotation;
	
	@ManyToOne
	@JoinColumn(name="variant_id")
	private CategoryVariants variant;
	
	@ManyToOne
	@JoinColumn(name="dealer_id")
	private Dealer dealer;

	@Column(name="exchangeVehicle")
	private String exchangeVehicle;
	
	@Column(name="paymentOption")
	private String paymentOption;
	
	@Column(name="finalPrice")
	private double finalPrice;

	public int getBooking_id() {
		return booking_id;
	}

	public void setBooking_id(int booking_id) {
		this.booking_id = booking_id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Quotation getQuotation() {
		return quotation;
	}

	public void setQuotation(Quotation quotation) {
		this.quotation = quotation;
	}

	public CategoryVariants getVariant() {
		return variant;
	}

	public void setVariant(CategoryVariants variant) {
		this.variant = variant;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
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
	
	
	
}
