package com.capgemini.webapp.spring.controller.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.webapp.common.constants.IApplicationConstants;
import com.capgemini.webapp.service.api.UserProfileService;
import com.capgemini.webapp.service.api.model.UserProfileModel;
import com.google.gson.JsonObject;

@RestController
@RequestMapping("/roles")
public class RoleRestController {

	@Autowired
	UserProfileService userProfileService;
	
	public static final Logger logger = LoggerFactory.getLogger(RoleRestController.class);
	
	/**
	 * Retrieves all Users
	 * @return
	 */
	@RequestMapping(value = "/listRoles/", method = RequestMethod.GET)
	public ResponseEntity<List<UserProfileModel>> listAllRoles() {

		List<UserProfileModel> roles = userProfileService.findAll();

		if (roles.isEmpty()) {

			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<UserProfileModel>>(roles, HttpStatus.OK);
	}

	
	@RequestMapping(value = { "/newRole/" }, method = RequestMethod.POST)
	public ResponseEntity<String> newRole(@RequestBody UserProfileModel userProfileBean) {

		logger.info("New Role::POST::Creating new Role::");

		boolean isCreated = false;
		String errorMessage = null;
		if (userProfileBean != null) {			

			try {
				isCreated = userProfileService.saveUserProfile(userProfileBean);
			} catch (org.hibernate.exception.ConstraintViolationException e) {
				errorMessage = "Duplicate entry "+userProfileBean.getType();
				System.out.println(" Error getConstraintName :"+e.getConstraintName());
				System.out.println(" Error getSQLException :"+e.getSQLException());
				System.out.println(" Error getLocalizedMessage :"+e.getLocalizedMessage());
			}
		}
		JsonObject responseObj = new JsonObject();

		if (isCreated) {
			responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_SUCCESS_CODE);
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "Role has been created successfully");

			return new ResponseEntity<String>(responseObj.toString(), HttpStatus.OK);
		}

		else {
			if (errorMessage == null)
				errorMessage = "Error Creating in New Role...";
			responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_ERROR_CODE);
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE, errorMessage);
			return new ResponseEntity<String>(responseObj.toString(), HttpStatus.OK);
		}
	}
	
@RequestMapping(value = { "/deleteRole/" }, method = RequestMethod.DELETE)
	
	public ResponseEntity<String> deleteRole(@RequestParam("userProfileId") String userProfileId) {

		logger.info("deleteUser::Deleting Role with userProfileId " + userProfileId);
		boolean status=false;
		String errorMessage=null;
		JsonObject responseObj = new JsonObject();
		try {
		if(userProfileService.findById(Integer.valueOf(userProfileId))!=null) {
		
			if(!userProfileService.isUserExistForRole(Integer.valueOf(userProfileId))) {
				
				try{
					status= userProfileService.deleteRoleByProfileID(userProfileId);
				}
				catch (org.hibernate.exception.ConstraintViolationException e) {
					errorMessage ="There are Users exist with this ROLE, so It cant be deleted.";
					System.out.println(" Error getConstraintName :"+e.getConstraintName());
					System.out.println(" Error getSQLException :"+e.getSQLException());
					System.out.println(" Error getLocalizedMessage :"+e.getLocalizedMessage());
				}
			}
			else {
				errorMessage="ROLE can not be deleted, its mapped to USER";
			}
		}	
		}catch(Exception e) {
			
			logger.error("Error deleting user:"+ e.getMessage());
			responseObj.addProperty(IApplicationConstants.REST_STATUS,IApplicationConstants.STATUS_ERROR_CODE );	
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE,"User deleted successfully" );
			return new ResponseEntity(responseObj.toString(), HttpStatus.OK);
		}
	if (status) {
			
			responseObj.addProperty(IApplicationConstants.REST_STATUS,IApplicationConstants.STATUS_SUCCESS_CODE );
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE,"User deleted successfully" );
			return new ResponseEntity(responseObj.toString(), HttpStatus.OK);
			// You many decide to return HttpStatus.NOT_FOUND
		}else {
			if(errorMessage==null)
				errorMessage="Error Deleting Role";
			responseObj.addProperty(IApplicationConstants.REST_STATUS,IApplicationConstants.STATUS_ERROR_CODE );
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE,errorMessage );			
			return new ResponseEntity(responseObj.toString(), HttpStatus.OK);
		}

	}
}
