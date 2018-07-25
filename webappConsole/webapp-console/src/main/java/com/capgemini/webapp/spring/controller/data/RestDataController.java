package com.capgemini.webapp.spring.controller.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.webapp.dao.api.entity.Product;
import com.capgemini.webapp.dao.api.entity.ProductCategory;
import com.capgemini.webapp.service.api.MasterDataService;
import com.capgemini.webapp.service.api.model.ProductCategoryModel;
import com.capgemini.webapp.service.api.model.ProductModel;

@RestController
@RequestMapping("/data")
@Secured("USER")
public class RestDataController {

	public static final Logger logger = LoggerFactory.getLogger(RestDataController.class);

	@Autowired
	MasterDataService dataService;

	/**
	 * Retrieve list of all products
	 * @return
	 */
	@RequestMapping(value = "/products/", method = RequestMethod.GET)

	public ResponseEntity<List<ProductModel>> listAllProducts() {
		
		List<ProductModel> prodList=null;
		try {
		
		 prodList = dataService.getAllProduct();
		
		
		if (prodList.isEmpty()) {
			
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		}catch(Exception e) {
			
			logger.error("Error retrieving products:"+e.getMessage());
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			
		}
		return new ResponseEntity<List<ProductModel>>(prodList, HttpStatus.OK);
	}

	/**
	 * Retrieve list of product category
	 * @return
	 */
	@RequestMapping(value = "/categories/", method = RequestMethod.GET)

	public ResponseEntity<List<ProductCategoryModel>> listAllProductCategory() {
		List<ProductCategoryModel> prodCatList=null;
	
		try {
			
		 prodCatList = dataService.getAllProductCategory();
		if (prodCatList!=null && prodCatList.isEmpty()) {
			
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		}catch(Exception e) {
			
			logger.error("Error retrieving productcategory:"+e.getMessage());
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			
		}
		return new ResponseEntity<List<ProductCategoryModel>>(prodCatList, HttpStatus.OK);
	}

	/**
	 * Search product based on product Id
	 * 
	 * @param prodId
	 * @return Product
	 */
	//@RequestMapping(value = { "/editProduct/{prodId}" }, method = RequestMethod.GET)
	@RequestMapping(value = { "/getProduct/" }, method = RequestMethod.GET)
	
	//public ResponseEntity<ProductModel> searchProduct(@PathVariable("prodId") String prodId) {
	public ResponseEntity<ProductModel> searchProduct(@RequestParam("prodId") String prodId) {
		
		logger.info("Retrieving Product Data with Id :" + prodId);
		ProductModel prod =null;
		try {
			
			prod = dataService.findProductByID(prodId);
			if (prod == null) {
				
				return new ResponseEntity( HttpStatus.NO_CONTENT);
				// You many decide to return HttpStatus.NOT_FOUND
			}
		}catch(Exception e) {
			
			logger.error("Error retrieving product:"+e.getMessage());
			return new ResponseEntity( HttpStatus.NO_CONTENT);
			
		}

		return new ResponseEntity<ProductModel>(prod, HttpStatus.OK);

	}

@RequestMapping(value = { "/searchProduct/" }, method = RequestMethod.GET)
@CrossOrigin(origins = "*")
public ResponseEntity<ProductModel> getProduct(@RequestHeader(value="prodId") String prodId) {
	//@RequestHeader(value="prodId") 
		//String prodId="P01";
		System.out.println("Search Product Data for :" + prodId);

		ProductModel prod = dataService.findProductByID(prodId);
		if (prod == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}

		return new ResponseEntity<ProductModel>(prod, HttpStatus.OK);

	}

	@RequestMapping(value = "/editProduct/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<String> updateProduct( @RequestBody  ProductModel productBean) {
		
		boolean status=false;
		logger.info("Upating Product Data with Id :" + productBean.getProdId());
		try {
			
		status=dataService.updateProduct(productBean, productBean.getProdId());
		
		}catch(Exception e) {
			logger.error("Error updating product details"+e.getMessage());
			return new ResponseEntity<String>("Error",HttpStatus.OK);
		}
		
		if(status) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
			
		}else {
			return new ResponseEntity<String>("error", HttpStatus.OK);
		}
		
	}
	
	/**
	 * Creates new product
	 * @param productBean
	 * @return
	 */
	@RequestMapping(value = "/createProduct/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createProduct( @RequestBody  ProductModel productBean) {
		
		logger.info("Creating Product Data with Id :" + productBean.getProdId());
		boolean isCreated=false;
		try {
		if(productBean!=null) {
			
			isCreated =  dataService.addProduct(productBean);
		} 
		}catch(Exception e) {
			logger.error("Error creating new product:"+e.getMessage());
			return new ResponseEntity<String>("Error", HttpStatus.OK);
		}
		if (isCreated)
			return new ResponseEntity<String>("success",HttpStatus.OK);
		else
			return new ResponseEntity<String>("Error", HttpStatus.OK);
	}
	
	@RequestMapping(value = { "/deleteProduct/" }, method = RequestMethod.DELETE)
	
	public ResponseEntity<String> deleteUser(@RequestParam("prodId") String prodId) {

		logger.info("deleteUser::Deleting user with prodId " + prodId);
		boolean status=false;
		try {
			
			if(dataService.findProductByID(prodId)!=null) {
			 status= dataService.deleteProductByProdId(prodId);
			}
			
		}catch(Exception e) {
			
			logger.error("Error deleting user:"+ e.getMessage());
			return new ResponseEntity("error", HttpStatus.OK);
		}
		if (status) {
			return new ResponseEntity("success", HttpStatus.OK);
			// You many decide to return HttpStatus.NOT_FOUND
		}else {
			return new ResponseEntity("error", HttpStatus.OK);
		}

	}
	
}
