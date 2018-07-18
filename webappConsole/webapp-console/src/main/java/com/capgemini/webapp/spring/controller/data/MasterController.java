package com.capgemini.webapp.spring.controller.data;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.util.UriComponentsBuilder;

import com.capgemini.webapp.service.api.MasterDataService;
import com.capgemini.webapp.service.api.model.MessageModel;
import com.capgemini.webapp.service.api.model.ProductCategoryModel;
import com.capgemini.webapp.service.api.model.ProductModel;
import com.capgemini.webapp.service.api.model.SMSMessageModel;
import com.capgemini.webapp.service.api.model.SMSModel;
import com.capgemini.webapp.spring.controller.BaseController;


@Controller
@RequestMapping("/")
@SessionAttributes("categories")

/**
 * MasterController to handles all CRUD requests related to Master data
 * @author pallapat
 *
 */
public class MasterController extends BaseController{

	@Autowired
	MasterDataService dataService;
	
	//public static final String REST_SERVICE_URI ="http://10.76.132.120:8280/UserManagement/1.0.0";
	public static final String REST_SERVICE_URI ="http://localhost:8082/pocwebapp";
	
	public static final Logger logger = LoggerFactory.getLogger(MasterController.class);
			
	@RequestMapping(value = { "/products" }, method = RequestMethod.GET)
	public String listProduct(ModelMap model) {

		logger.info("MasterController::listProduct::Retrieving Product List");
		List<ProductModel> prodList = new ArrayList();
		try {
			
			URI uri = new URI(REST_SERVICE_URI+"/data/products/");
			RestTemplate restTemplate = new RestTemplate(); 
			List<LinkedHashMap<String, Object>> productMap = restTemplate.getForObject(uri, List.class);
			if(productMap!=null){	
				
				for(LinkedHashMap<String, Object> prodEntity : productMap){
					
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

				logger.info("MasterController::listProduct::No user exist----------");
			}
		} catch (URISyntaxException e) {
			logger.error("MasterController::listProduct::"+e.getMessage());
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
		
		logger.info("MasterController::editProduct::Update Product::"+prodId);
		URI uri;
		ProductModel product=null;
		ProductCategoryModel prodCats=null;
		try {
			
			uri = new URI(REST_SERVICE_URI+"/data/editProduct/");			
			RestTemplate restTemplate = new RestTemplate(); 
			
			//HttpHeaders headers = new HttpHeaders();
			//headers.setContentType(MediaType.TEXT_PLAIN);
			//headers.set("Authorization", "Bearer "+accessToken);
			//headers.set("prodId", prodId);
			//HttpEntity<String> entity = new HttpEntity<String>(null,headers);
			//product = restTemplate.exchange(uri, entity, ProductModel.class);
			
			
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(REST_SERVICE_URI+"/data/getProduct/")
			       .queryParam("prodId", prodId);
			
			product = restTemplate.getForObject(builder.toUriString(), ProductModel.class);	
			
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
				
			}
				
			if(product!=null) {
				model.addAttribute("categories", cateogryList);
				model.addAttribute("productBean", product);
				model.addAttribute("edit", true);
				model.addAttribute("loggedinuser", super.getPrincipal());
				
			}else{
				
				logger.info("MasterController::editProduct::No Product exist");
				
			}
			
		} catch (Exception e) {
			logger.error("MasterController::editProduct:"+e.getMessage());
			
		}		
		logger.info("MasterController::editProduct::Updated Product-"+prodId);
		return "updateProduct";
	}
	
	
	/**
	 * This method will be called on form submission.Handles POST request for updating user in database. 
	 * It also validates the user input
	 */
	
	@RequestMapping(value = { "/editProduct{prodId}" }, method = RequestMethod.POST)	
	public String updateProduct( @ModelAttribute("user") @Valid ProductModel productBean, BindingResult result,		
			ModelMap model, @PathVariable String prodId) {
		
		logger.info("MasterController::editProduct::Post::Update Product-"+prodId);
		if (result.hasErrors()) {
			return "updateProduct";
		}
		
		RestTemplate restTemplate = new  RestTemplate();
		URI uri =restTemplate.postForLocation(REST_SERVICE_URI+ "/data/editProduct/", productBean, ProductModel.class);
	
		model.addAttribute("success", "Product updated successfully");
		model.addAttribute("loggedinuser", super.getPrincipal());
		logger.info("MasterController::updateProduct::Updated Product-"+prodId);
		return "productsuccess";
	}
	
	
	/**
	 * This method will delete an user by it's SSOID value.
	 */
	@RequestMapping(value = { "/deleteProduct-{prodId}" }, method = RequestMethod.GET)
	public String deleteProduct(@PathVariable String prodId) {
		
		logger.info("MasterController::deleteProduct::delete Product-"+prodId);
		dataService.deleteProductByProdId(prodId);
		logger.info("MasterController::deleteProduct::deleted Product-"+prodId);
		return "redirect:/products";
	}
	
	/**
	 * This method will provide the medium to add a new user.
	 */
	@RequestMapping(value = { "/newproduct" }, method = RequestMethod.GET)
	public String newProduct(ModelMap model) {
		
		logger.info("MasterController::newProduct::Creating new Product::");
		
		ProductModel prod = new ProductModel();
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
			
		}
		if(prod!=null) {
			
			model.addAttribute("productBean", prod);
			model.addAttribute("categories", cateogryList);
			model.addAttribute("edit", false);
			model.addAttribute("loggedinuser", super.getPrincipal());
			
		}
		logger.info("MasterController::newProduct::Get::Creating new Product::");
			
		return "updateProduct";
	}
	
	@RequestMapping(value = { "/newproduct" }, method = RequestMethod.POST)
	public String newProduct( @ModelAttribute("productBean") @Valid ProductModel productBean, BindingResult result, ModelMap model) {

		logger.info("MasterController::newProduct::POST::Creating new Product::");
		
		if (result.hasErrors()) {
			return "updateProduct";
		}
		boolean addedProd=false;
		if(productBean!=null) {
			
			addedProd=  dataService.addProduct(productBean);
		}
		if(addedProd) {
		model.addAttribute("success",
				"Product " +productBean.getName() +" added  successfully");
		model.addAttribute("loggedinuser", super.getPrincipal());
		logger.info("MasterController::newProduct::Executed method successfully");
		return "productsuccess";
		
		}else {
			
			
			model.addAttribute("productBean", productBean);
			FieldError ssoError = new FieldError("productBean", "prodId", "Product already exist");
			result.addError(ssoError);

			logger.info("MasterController::newProduct::Executed method");
			
			return "updateProduct";
		
			
		}
	}

	@RequestMapping(value = { "/sendSMS" }, method = RequestMethod.GET)
	public String sendSMSNotification( ModelMap model) {
		
		logger.info("MasterController::sendSMSNotification::GET");
		MessageModel smsMsgBean=new MessageModel();
		smsMsgBean.setMessage("Enter Text Message");
		smsMsgBean.setReciepents("9987074418");		
		model.addAttribute("smsMsgBean",smsMsgBean);		
		logger.info("MasterController::sendSMSNotification::GET method executed");
		return "smsgateway";
	}

	@RequestMapping(value = { "/sendSMS" }, method = RequestMethod.POST)
	public String sendSMSNotification( @ModelAttribute("smsMsgBean") @Valid MessageModel smsMsgBean, BindingResult result, ModelMap model) {
	
		logger.info("MasterController::sendSMS::POST::Sending SMS");
		
		SMSMessageModel msg=new SMSMessageModel();
		String recipients="";
		if(smsMsgBean!=null) {
			
			msg.setMessage(smsMsgBean.getMessage());
			recipients=smsMsgBean.getReciepents();
			if(recipients!=null && recipients.length()>0) {
			
			ArrayList<String> recipientsList = new ArrayList<String>(Arrays.asList(recipients.split("\\s*,\\s*")));
			msg.setTo(recipientsList);
		}
			
		}
		SMSModel  sms=new SMSModel();
		List<SMSMessageModel> lstSMS=new ArrayList<SMSMessageModel>();
		lstSMS.add(msg);
		sms.setSmsmessage(lstSMS);
		
		RestTemplate restTemplate = new  RestTemplate();
		ResponseEntity<String> response=restTemplate.postForEntity(REST_SERVICE_URI+ "/util/sms/", sms, String.class);
	
		String status=response.getBody();
		
		logger.info("MasterController::sendSMSNotification::POST method executed");
		return "smsgateway";
		
	}
		
	
	
	}


