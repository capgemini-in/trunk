package com.capgemini.webapp.dao.api;

import java.util.List;

import com.capgemini.webapp.dao.api.entity.ProductCategory;

public interface ProductCategoryDao {
	List<ProductCategory> findAllProductCategory();
}
