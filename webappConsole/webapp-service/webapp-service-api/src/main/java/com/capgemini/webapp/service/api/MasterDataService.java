package com.capgemini.webapp.service.api;

import java.util.List;
import com.capgemini.webapp.service.api.model.ProductCategoryModel;
import com.capgemini.webapp.service.api.model.ProductModel;
/**
 * Interface for handling Master data related operations
 * @author pallapat
 *
 */
public interface MasterDataService {

	List<ProductModel> getAllProduct();

	ProductModel findProductByID(String prodId);

	boolean addProduct(ProductModel prod);

	void updateProduct(ProductModel prod, String prodId);

	void deleteProdById(Integer prodId);

	List<ProductCategoryModel> getAllProductCategory();

	void deleteProductByProdId(String prodId);
}