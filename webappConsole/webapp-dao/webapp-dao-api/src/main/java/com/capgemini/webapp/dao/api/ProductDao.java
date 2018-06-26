package com.capgemini.webapp.dao.api;


import java.util.List;

import com.capgemini.webapp.dao.api.entity.Product;
	

public interface ProductDao {


	public List<Product> findAllProducts();
	public Product findByProdID(String prodId);
	public void saveProduct(Product prod);
	public void deleteByProdId(String prodId);
}
