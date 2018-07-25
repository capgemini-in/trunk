package com.capgemini.webapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.webapp.dao.api.ProductCategoryDao;
import com.capgemini.webapp.dao.api.ProductDao;
import com.capgemini.webapp.dao.api.entity.Product;
import com.capgemini.webapp.dao.api.entity.ProductCategory;
import com.capgemini.webapp.service.api.MasterDataService;
import com.capgemini.webapp.service.api.model.ProductCategoryModel;
import com.capgemini.webapp.service.api.model.ProductModel;
import com.capgemini.webapp.service.api.model.UserModel;


@Service("dataService")
@Transactional

public class MasterDataServiceImpl implements MasterDataService {

	@Autowired
	ProductDao dao;
	
	@Autowired
	ProductCategoryDao prodCatDao;
	
	public static final Logger logger = LoggerFactory.getLogger(MasterDataServiceImpl.class);
	
	@Override
	public List<ProductModel> getAllProduct() {
		
		logger.debug("getAllProduct+Retrieving all products");
		
		List<ProductModel> prodList = null;
		try {
			
			prodList= this.mapList(dao.findAllProducts(),ProductModel.class);
			
		}catch(Exception e) {
			
			logger.error("Error retrieving products:"+e.getMessage());
			return null;
		}
		logger.debug("getAllProduct+Completed Retrieving products list");

		return prodList;
		
		
	}

	@Override
	public ProductModel findProductByID(String prodId) {
		
		logger.debug("findProductByID+Retrieving product by Id:"+prodId);
		ProductModel prodModel =null;
		try {	
			
			if(dao.findByProdID(prodId)!=null)
				prodModel= new DozerBeanMapper().map(dao.findByProdID(prodId), ProductModel.class);
			
		}catch(Exception e) {
			
			logger.error("Error retrieving products:"+e.getMessage());
			
			return null;
		}
		return prodModel;
	
		//return dao.findByProdID(prodId);
	}

	@Override
	public boolean addProduct(ProductModel prodModel) {
		
		Product prod =new DozerBeanMapper().map(prodModel,Product.class);
		if (isProductUnique(prod.getId(), prod.getProdId())) {
				dao.saveProduct(prod);
				return true;
		}else {
			return false;
		}
		
	}
	
	private boolean isProductUnique(Integer id, String prodId) {
		ProductModel product = findProductByID(prodId);
		return (product == null);
	}

	@Override
	public boolean updateProduct(ProductModel prodModel, String prodId) {
		

		boolean status=false;
		try {
			
			Product prod = new DozerBeanMapper().map(prodModel,Product.class);
			Product prodEntity=null;
			if(prodId!=null) {
				
				prodEntity= dao.findByProdID(prodId);
				
			}else {
				prodEntity=dao.findByProdID(prod.getProdId());
			}
			
			if (prodEntity != null) {
				
				prodEntity.setName(prod.getName());
				prodEntity.setPrice(prod.getPrice());
				prodEntity.setDescription(prod.getDescription());
				prodEntity.setQuantity(prod.getQuantity());
				prodEntity.setProdCategory(prod.getProdCategory());
				dao.saveProduct(prodEntity);
				status=true;
				}else
					logger.debug("updateProduct+Product does not exist with:"+prodId);
			 
		}catch(Exception e) {			
			
			logger.error("Error updating bean"+ e.getMessage());
			return false;
			
		}
		return status;
		
		}
	
	@Override
	public List<ProductCategoryModel> getAllProductCategory() {
		return this.mapCatList(prodCatDao.findAllProductCategory(),ProductCategoryModel.class);
		//return  prodCatDao.findAllProductCategory();
	}

	@Override
	public boolean deleteProductByProdId(String prodId) {
		boolean isDeleted=false;
		try { 
			dao.deleteByProdId(prodId);
			isDeleted=true;
			
		}catch(Exception e) {
			isDeleted=false;
			return isDeleted;
		}
		return isDeleted;
		
	}
	
	/*this is Mapper for List*/
    private List<ProductModel> mapList(List<Product> fromList, final Class<ProductModel> toClass) {
	    return fromList
	            .stream()
	            .map(from -> new DozerBeanMapper().map(from, toClass))
	            .collect(Collectors.toList());
	}

    /*this is Mapper for List*/
    private List<ProductCategoryModel> mapCatList(List<ProductCategory> fromList, final Class<ProductCategoryModel> toClass) {
	    return fromList
	            .stream()
	            .map(from -> new DozerBeanMapper().map(from, toClass))
	            .collect(Collectors.toList());
	}
}
