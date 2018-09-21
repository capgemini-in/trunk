package com.capgemini.webapp.spring.controller.data;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.webapp.common.constants.IApplicationConstants;
import com.capgemini.webapp.service.api.BusinessTypeService;
import com.capgemini.webapp.service.api.DealerService;
import com.capgemini.webapp.service.api.LocationService;
import com.capgemini.webapp.service.api.UserService;
import com.capgemini.webapp.service.api.VariantDetailsService;
import com.capgemini.webapp.service.api.model.BusinessTypeModel;
import com.capgemini.webapp.service.api.model.CategoryModel;
import com.capgemini.webapp.service.api.model.CategoryVariantsModel;
import com.capgemini.webapp.service.api.model.CountryModel;
import com.capgemini.webapp.service.api.model.DealerModel;
import com.capgemini.webapp.service.api.model.QuotationModel;
import com.capgemini.webapp.service.api.model.SubMenuCategoryModel;
import com.capgemini.webapp.service.api.model.VariantDetailsModel;
import com.google.gson.JsonObject;

@RestController
@RequestMapping("/model")

public class ModelDataController {

	public static final Logger logger = LoggerFactory.getLogger(ModelDataController.class);

	@Autowired
	LocationService locationService;

	@Autowired
	BusinessTypeService menuService;	

	@Autowired
	DealerService dealerService;
	
	@Autowired
	VariantDetailsService variantDetailsService;


	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/country/", method = RequestMethod.GET)

	public ResponseEntity<List<CountryModel>> listCountriesDetail() {

		List<CountryModel> countryList = null;
		JsonObject responseObj = new JsonObject();
		// Gson gson = new Gson();
		// jsonInString = gson.toJson(smsModel);
		try {

			countryList = locationService.getAllCountries();

			if (countryList != null && countryList.isEmpty()) {

				responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_NOCONTENT_CODE);
				responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "No Data Exist");
				return new ResponseEntity(HttpStatus.NO_CONTENT);

				// You many decide to return HttpStatus.NOT_FOUND
			}
		} catch (Exception e) {
			responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_ERROR_CODE);
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "Error retrieving products");
			logger.error("Error retrieving products:" + e.getMessage());
			return new ResponseEntity(HttpStatus.NO_CONTENT);

		}
		return new ResponseEntity<List<CountryModel>>(countryList, HttpStatus.OK);
	}

	@RequestMapping(value = "/menus/", method = RequestMethod.GET)

	public ResponseEntity<BusinessTypeModel> listAllMenus() {

		BusinessTypeModel menuList = null;
		JsonObject responseObj = new JsonObject();
		// Gson gson = new Gson();
		// jsonInString = gson.toJson(smsModel);
		try {

			menuList = menuService.getAllBusinessMenus();

			if (menuList == null) {

				responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_NOCONTENT_CODE);
				responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "No Data Exist");
				return new ResponseEntity(HttpStatus.NO_CONTENT);

				// You many decide to return HttpStatus.NOT_FOUND
			}
		} catch (Exception e) {
			responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_ERROR_CODE);
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "Error retrieving Menus");
			logger.error("Error retrieving Business Menus:" + e.getMessage());
			return new ResponseEntity(HttpStatus.NO_CONTENT);

		}
		return new ResponseEntity<BusinessTypeModel>(menuList, HttpStatus.OK);
	}

	@RequestMapping(value = "/category/", method = RequestMethod.GET)

	public ResponseEntity<List<CategoryModel>> getCategoryDetail(@RequestParam(value = "subMenuId") String subMenuId,
			@RequestParam(value = "busiTypeId") String busiTypeId) {

		List<CategoryModel> catList = null;
		JsonObject responseObj = new JsonObject();

		try {

			catList = locationService.getCategoryDetail(subMenuId, busiTypeId);

			if (catList != null && catList.isEmpty()) {

				responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_NOCONTENT_CODE);
				responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "No Data Exist");
				return new ResponseEntity(HttpStatus.NO_CONTENT);

				// You many decide to return HttpStatus.NOT_FOUND
			}
		} catch (Exception e) {
			responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_ERROR_CODE);
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "Error retrieving category");
			logger.error("Error retrieving category:" + e.getMessage());
			return new ResponseEntity(HttpStatus.NO_CONTENT);

		}
		return new ResponseEntity<List<CategoryModel>>(catList, HttpStatus.OK);
	}

	@RequestMapping(value = "/variant/", method = RequestMethod.GET)

	public ResponseEntity<List<CategoryVariantsModel>> getVariantsForCategory(
			@RequestParam(value = "subMenuId") String subMenuId) {

		// BusinessSubMenuModel subMenuModel=null;
		SubMenuCategoryModel subMenuModel = null;
		JsonObject responseObj = new JsonObject();
		List<CategoryVariantsModel> variantsList = new ArrayList<CategoryVariantsModel>();
		try {

			subMenuModel = locationService.getVariantsForCategory(subMenuId);

			for (CategoryModel cat : subMenuModel.getCategory()) {

				for (CategoryVariantsModel variantModel : cat.getVariants()) {
					
					variantModel.setCategoryName(cat.getCategoryName());
					variantsList.add(variantModel);

				}
			}

			if (subMenuModel == null) {

				responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_NOCONTENT_CODE);
				responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "No Data Exist");
				return new ResponseEntity(HttpStatus.NO_CONTENT);

				// You many decide to return HttpStatus.NOT_FOUND
			}
		} catch (Exception e) {
			responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_ERROR_CODE);
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "Error retrieving Category Variant");
			logger.error("Error retrieving category:" + e.getMessage());
			return new ResponseEntity(HttpStatus.NO_CONTENT);

		}

		// return new ResponseEntity<SubMenuCategoryModel>(subMenuModel, HttpStatus.OK);
		return new ResponseEntity<List<CategoryVariantsModel>>(variantsList, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/variantdetails/", method = RequestMethod.GET)

	public ResponseEntity<List<VariantDetailsModel>> getVariantDetails(
			@RequestParam(value = "variantId") String variantId,@RequestParam(value = "fuelType") String fuelType) {

		List<VariantDetailsModel> detailsModel = null;
		JsonObject responseObj = new JsonObject();
		try {

			detailsModel = variantDetailsService.getVariantDetails(variantId,fuelType);

			if (detailsModel == null) {

				responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_NOCONTENT_CODE);
				responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "No Data Exist");
				return new ResponseEntity(HttpStatus.NO_CONTENT);

				// You many decide to return HttpStatus.NOT_FOUND
			}
		} catch (Exception e) {
			responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_ERROR_CODE);
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "Error retrieving Variant Details");
			logger.error("Error retrieving category:" + e.getMessage());
			return new ResponseEntity(HttpStatus.NO_CONTENT);

		}

		return new ResponseEntity<List<VariantDetailsModel>>(detailsModel, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/dealers/", method = RequestMethod.GET)
	public ResponseEntity<List<DealerModel>> getDealerbyStateCity(@RequestParam(value = "stateID") String stateId,
			@RequestParam(value = "cityId") String cityId) {
		
		List<DealerModel> dealerList = null;
		JsonObject responseObj = new JsonObject();

		try {

			dealerList = dealerService.getAllDealerforStateCity(stateId, cityId);

			if (dealerList != null && dealerList.isEmpty()) {

				responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_NOCONTENT_CODE);
				responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "No Data Exist");
				return new ResponseEntity(HttpStatus.NO_CONTENT);

				// You many decide to return HttpStatus.NOT_FOUND
			}
		} catch (Exception e) {
			responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_ERROR_CODE);
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "Error retrieving category");
			logger.error("Error retrieving category:" + e.getMessage());
			return new ResponseEntity(HttpStatus.NO_CONTENT);

		}
		return new ResponseEntity<List<DealerModel>>(dealerList, HttpStatus.OK);	
		

	}

	@RequestMapping(value = "/quotationRequest/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> processQuotationRequest(@RequestBody  QuotationModel quotationModel){
		
		
		logger.debug("processQuotationRequest");
		if(quotationModel.getUser()!=null)
			userService.saveUser(quotationModel.getUser());
		boolean isQuotationCreated=	dealerService.processQuotationRequest(quotationModel);		
		
		return  new ResponseEntity(HttpStatus.OK);
	}
	


}
