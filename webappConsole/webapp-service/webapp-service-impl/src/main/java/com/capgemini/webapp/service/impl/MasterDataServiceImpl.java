package com.capgemini.webapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
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


@Service("dataService")
@Transactional

public class MasterDataServiceImpl implements MasterDataService {

	@Autowired
	ProductDao dao;
	
	@Autowired
	ProductCategoryDao prodCatDao;
	@Override
	public List<ProductModel> getAllProduct() {
		return this.mapList(dao.findAllProducts(),ProductModel.class);
		//return dao.findAllProducts();
		
	}

	@Override
	public ProductModel findProductByID(String prodId) {
		ProductModel prodModel =null;
		if(dao.findByProdID(prodId)!=null)
			prodModel= new DozerBeanMapper().map(dao.findByProdID(prodId), ProductModel.class);
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
	public void updateProduct(ProductModel prodModel, String prodId) {
		
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
			
			}
		 dao.saveProduct(prodEntity);
		
		}
	

	@Override
	public void deleteProdById(Integer prodId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ProductCategoryModel> getAllProductCategory() {
		return this.mapCatList(prodCatDao.findAllProductCategory(),ProductCategoryModel.class);
		//return  prodCatDao.findAllProductCategory();
	}

	@Override
	public void deleteProductByProdId(String prodId) {
		dao.deleteByProdId(prodId);
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
