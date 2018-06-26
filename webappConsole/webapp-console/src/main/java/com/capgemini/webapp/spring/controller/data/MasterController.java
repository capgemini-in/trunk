package com.capgemini.webapp.spring.controller.data;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

import com.capgemini.webapp.service.api.MasterDataService;
import com.capgemini.webapp.service.api.model.ProductCategoryModel;
import com.capgemini.webapp.service.api.model.ProductModel;
import com.capgemini.webapp.spring.controller.BaseController;


@Controller
@RequestMapping("/")
@SessionAttributes("categories")
/**
 * MasterController to handles all data related requests
 * @author pallapat
 *
 */
public class MasterController extends BaseController{

	@Autowired
	MasterDataService dataService;
	
	public static final String REST_SERVICE_URI = "http://localhost:8091/pocwebapp" ;
	
	
	
	@RequestMapping(value = { "/products" }, method = RequestMethod.GET)
	public String listProduct(ModelMap model) {

		 
		List<ProductModel> prodList = new ArrayList();
		try {
			
		
			URI uri = new URI(REST_SERVICE_URI+"/data/products/");
			RestTemplate restTemplate = new RestTemplate(); 
			List<LinkedHashMap<String, Object>> productMap = restTemplate.getForObject(uri, List.class);
			if(productMap!=null){	
				
				for(LinkedHashMap<String, Object> prodEntity : productMap){
					System.out.println("Product : id="+prodEntity.get("id")+", prodId="+prodEntity.get("prod_id")+", name="+prodEntity.get("name")+", description="+prodEntity.get("description"));;
					ProductModel prod = new ProductModel();
					prod.setProdId(prodEntity.get("prodId").toString());
					prod.setName(prodEntity.get("name").toString());
					prod.setDescription(prodEntity.get("description").toString());
					prod.setQuantity(Integer.valueOf(prodEntity.get("quantity").toString()));
					prod.setId(Integer.valueOf(prodEntity.get("id").toString()));
					prod.setPrice(Integer.valueOf(prodEntity.get("price").toString()));
					LinkedHashMap<String, Object> prodCategory=(LinkedHashMap<String, Object>) prodEntity.get("prodCategory");
				
					ProductCategoryModel category= new ProductCategoryModel();
					if(prodCategory!=null && prodCategory.get("id")!=null) {
						category.setId(Integer.valueOf(prodCategory.get("id").toString()));
						category.setType(prodCategory.get("type").toString());
						category.setCatId(prodCategory.get("catId").toString());
					}
					prod.setProdCategory(category);
					prodList.add(prod);
				}
			}else{
				System.out.println("No user exist----------");
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("productList", prodList);
		model.addAttribute("loggedinuser", super.getPrincipal());
		
		return "productList";
	}


	/**
	 * This method updates existing Product details
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value = { "/editProduct{prodId}" }, method = RequestMethod.GET)
	public String editProduct(@PathVariable("prodId") String prodId, ModelMap model) {
		
		System.out.println("Edit Product prodId:"+ prodId);
		URI uri;
		ProductModel product=null;
		ProductCategoryModel prodCats=null;
		//EditProductBean prodBean=new EditProductBean();
		try {
			
			uri = new URI(REST_SERVICE_URI+"/data/updateProduct/"+prodId);			
			RestTemplate restTemplate = new RestTemplate(); 			
			product = restTemplate.getForObject(uri, ProductModel.class);	
			//prodBean.setProd(product);
			List<ProductCategoryModel> cateogryList=new ArrayList<ProductCategoryModel>();
						
		
			List<LinkedHashMap<String, Object>> categoryMap = restTemplate.getForObject(REST_SERVICE_URI+"/data/categories/", List.class);
			
			if(categoryMap!=null){	
				
				for(LinkedHashMap<String, Object> prodCatEntity : categoryMap){
					
					ProductCategoryModel catObj=new ProductCategoryModel();
					catObj.setId(Integer.valueOf(prodCatEntity.get("id").toString()));
					catObj.setCatId(prodCatEntity.get("catId").toString());
					catObj.setType(prodCatEntity.get("type").toString());
					cateogryList.add(catObj);
					
				}
				//prodBean.setListCat(cateogryList);
				
			}
				
			if(product!=null) {
				model.addAttribute("categories", cateogryList);
				model.addAttribute("productBean", product);
				model.addAttribute("edit", true);
				model.addAttribute("loggedinuser", super.getPrincipal());
				
			}else{
				
				System.out.println("No Product exist----------");
			}
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return "updateProduct";
	}
	
	/**
	 * This method will be called on form submission, handling POST request for
	 * updating user in database. It also validates the user input
	 */
	
	@RequestMapping(value = { "/editProduct{prodId}" }, method = RequestMethod.POST)
	
	
	public String updateProduct(@Valid ProductModel productBean, BindingResult result,		
			ModelMap model, @PathVariable String prodId) {
		
		
		if (result.hasErrors()) {
			return "updateProduct";
		}
	
			
			///URI uri = new URI(REST_SERVICE_URI+"/data/updateProduct/");
			//RestTemplate restTemplate = new  RestTemplate();
			//URI uri =restTemplate.postForLocation(REST_SERVICE_URI+"/data/editProduct/", productBean.getProd(), Product.class);
						
		
			dataService.updateProduct(productBean, prodId);

		model.addAttribute("success", "Product updated successfully");
		model.addAttribute("loggedinuser", super.getPrincipal());
		return "productsuccess";
	}
	
	
	/**
	 * This method will delete an user by it's SSOID value.
	 */
	@RequestMapping(value = { "/deleteProduct-{prodId}" }, method = RequestMethod.GET)
	public String deleteProduct(@PathVariable String prodId) {
		
		dataService.deleteProductByProdId(prodId);
		return "redirect:/products";
	}
	
	/**
	 * This method will provide the medium to add a new user.
	 */
	@RequestMapping(value = { "/newproduct" }, method = RequestMethod.GET)
	public String newProduct(ModelMap model) {
		

		ProductModel prod = new ProductModel();
		//EditProductBean productBean =new EditProductBean();
		//productBean.setProd(prod);
		List<ProductCategoryModel> cateogryList=new ArrayList<ProductCategoryModel>();
		RestTemplate restTemplate=new RestTemplate();
		List<LinkedHashMap<String, Object>> categoryMap = restTemplate.getForObject(REST_SERVICE_URI+"/data/categories/", List.class);
		
		if(categoryMap!=null){	
			
			for(LinkedHashMap<String, Object> prodCatEntity : categoryMap){
				
				ProductCategoryModel catObj=new ProductCategoryModel();
				catObj.setId(Integer.valueOf(prodCatEntity.get("id").toString()));
				catObj.setCatId(prodCatEntity.get("catId").toString());
				catObj.setType(prodCatEntity.get("type").toString());
				cateogryList.add(catObj);
				
			}
			//productBean.setListCat(cateogryList);
			
		}
		if(prod!=null) {
			
			model.addAttribute("productBean", prod);
			model.addAttribute("catList", cateogryList);
			model.addAttribute("edit", false);
			model.addAttribute("loggedinuser", super.getPrincipal());
			
		}
		return "updateProduct";
	}
	
	@RequestMapping(value = { "/newproduct" }, method = RequestMethod.POST)
	public String newProduct(@Valid ProductModel productBean, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			return "updateProduct";
		}
		boolean addedProd=false;
		System.out.println("Creating new Product");
		if(productBean!=null) {
			System.out.println("newProduct:"+productBean);
			addedProd=  dataService.addProduct(productBean);
		}
		if(addedProd) {
		model.addAttribute("success",
				"Product " +productBean.getName() +" added  successfully");
		model.addAttribute("loggedinuser", super.getPrincipal());
		return "productsuccess";
		}else {
			
			
			model.addAttribute("productBean", productBean);
			/*FieldError ssoError = new FieldError("productBean", "prod.prodId", "Product already exist");
			result.addError(ssoError);*/
			return "updateProduct";
		
			//return "redirect:/newproduct";
			
		}
		// return "success";
		

	}

	}
