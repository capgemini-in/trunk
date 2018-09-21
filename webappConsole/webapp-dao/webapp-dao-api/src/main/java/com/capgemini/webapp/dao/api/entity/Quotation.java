package com.capgemini.webapp.dao.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="quotation")
/**
 * Quotation Entity Class
 * @author pallapat
 *
 */
public class Quotation  implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "quotation_id")
	private int quotation_id;
	
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	

	@ManyToOne
	@JoinColumn(name="dealer_id")
	private Dealer dealer;


	@ManyToOne
	@JoinColumn(name="variant_id")
	private CategoryVariants variant;


	@ManyToOne
	@JoinColumn(name="city_id")
	private City city;

	@Column(name = "discounted_price")
	private double discountedPrice;

	public int getQuotation_id() {
		return quotation_id;
	}

	public void setQuotation_id(int quotation_id) {
		this.quotation_id = quotation_id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public CategoryVariants getVariant() {
		return variant;
	}

	public void setVariant(CategoryVariants variant) {
		this.variant = variant;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public double getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(double discountedPrice) {
		this.discountedPrice = discountedPrice;
	}
	

}
	

