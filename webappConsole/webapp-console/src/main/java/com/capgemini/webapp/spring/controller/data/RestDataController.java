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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	// -------------------Retrieve All
	// Users---------------------------------------------

	@RequestMapping(value = "/products/", method = RequestMethod.GET)

	public ResponseEntity<List<ProductModel>> listAllProducts() {
		List<ProductModel> prodList = dataService.getAllProduct();
		if (prodList.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<ProductModel>>(prodList, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/categories/", method = RequestMethod.GET)

	public ResponseEntity<List<ProductCategoryModel>> listAllProductCategory() {
		List<ProductCategoryModel> prodCatList = dataService.getAllProductCategory();
		if (prodCatList.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<ProductCategoryModel>>(prodCatList, HttpStatus.OK);
	}

	/**
	 * Search product based on product Id
	 * 
	 * @param prodId
	 * @return Product
	 */
	@RequestMapping(value = { "/updateProduct/{prodId}" }, method = RequestMethod.GET)
	
	public ResponseEntity<ProductModel> searchProduct(@PathVariable("prodId") String prodId) {
		
		System.out.println("Search Product Data for :" + prodId);

		ProductModel prod = dataService.findProductByID(prodId);
		if (prod == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}

		return new ResponseEntity<ProductModel>(prod, HttpStatus.OK);

	}

	@RequestMapping(value = "/editProduct/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	//@ResponseBody()
	public ResponseEntity<String> updateProduct( @RequestBody  ProductModel productBean) {

		System.out.println("Fetch Data for :");		
		dataService.updateProduct(productBean, productBean.getProdId());
		/*HttpHeaders hea
	        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
	        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		*///
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
