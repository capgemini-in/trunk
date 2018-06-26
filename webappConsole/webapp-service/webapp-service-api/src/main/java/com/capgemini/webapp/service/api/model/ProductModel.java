package com.capgemini.webapp.service.api.model;

import java.io.Serializable;


import com.capgemini.webapp.dao.api.entity.ProductCategory;
/**
 * Model for Product DAO Entity
 * @author pallapat
 *
 */
public class ProductModel implements Serializable{
	
	private Integer id;

	private String prodId;
	
	
	private String name;
	
	
	private String description;
	
	
	private int quantity;
	
	
	private int price;
	
	private ProductCategoryModel prodCategory;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public ProductCategoryModel getProdCategory() {
		return prodCategory;
	}

	public void setProdCategory(ProductCategoryModel prodCategory) {
		this.prodCategory = prodCategory;
	}
	
	

}
