package com.capgemini.webapp.spring.controller.user;

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
import com.capgemini.webapp.service.api.UserProfileService;
import com.capgemini.webapp.service.api.UserService;
import com.capgemini.webapp.service.api.model.UserModel;
import com.google.gson.JsonObject;

@RestController
@RequestMapping("/api")
/**
 * This user rest service handle CRUD operation for user
 * 
 * @author pallapat
 *
 */
public class UserRestController {

	@Autowired
	UserService userService;

	@Autowired
	UserProfileService userProfileService;

	public static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

	/**
	 * Retrieves all Users
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<UserModel>> listAllUsers() {
		
		logger.info(":: UserRestController : listAllUsers() method called ");
		List<UserModel> users = userService.findAllUsers();
		if (users.isEmpty()) {

			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<UserModel>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = { "/newUser/" }, method = RequestMethod.POST)
	public ResponseEntity<String> newUser(@RequestBody UserModel userBean) {

		logger.info(":: UserRestController : newUser() method called ");
		logger.info("Creating new user::"+userBean.getFirstName()+" "+userBean.getLastName());

		boolean isCreated = false;

		if (userBean != null) {
			/*
			 * if(userBean.getSsoId()==null) { String ssoId=
			 * userService.createSSOID(userBean.getFirstName(), userBean.getLastName());
			 * if(ssoId !=null) userBean.setSsoId(ssoId); userBean.setPassword("guest123");
			 * }
			 */

			// if (userService.isUserSSOUnique(userBean.getId(), userBean.getSsoId())) {

			isCreated = userService.saveUser(userBean);
			// }

		}
		JsonObject responseObj = new JsonObject();

		if (isCreated) {
			logger.info("New User has been created successfully");
			responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_SUCCESS_CODE);
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "User created successfully");
			return new ResponseEntity<String>(responseObj.toString(), HttpStatus.OK);
		}

		else {
			logger.info("Error creating user for "+userBean.getFirstName());
			responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_ERROR_CODE);
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "Error creating user");
			return new ResponseEntity<String>(responseObj.toString(), HttpStatus.OK);
		}

	}

	@RequestMapping(value = { "/getUser/" }, method = RequestMethod.GET)

	public ResponseEntity<UserModel> getUser(@RequestParam("ssoId") String ssoId) {

		logger.info(":: UserRestController : getUser() method called ");
		logger.info(":: Retrieving user with ssoId " + ssoId);

		UserModel user = userService.findBySSO(ssoId);
		if (user == null) {
			
			logger.info(":: Unabel to find user with ssoId " + ssoId);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<UserModel>(user, HttpStatus.OK);
	}

	/**
	 * Updates existing product
	 * 
	 * @param userBean
	 * @return
	 */
	@RequestMapping(value = "/editUser/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateUser(@RequestBody UserModel userBean) {
		
		logger.info(":: UserRestController : updateUser() method called ");
		JsonObject responseObj = new JsonObject();
		boolean status = false;
		try {
			status = userService.updateUser(userBean);
		} catch (Exception e) {

			logger.error("Error updating product details" + e.getMessage());
			responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_ERROR_CODE);
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "Error updating user");
			return new ResponseEntity<String>(responseObj.toString(), HttpStatus.OK);

		}

		if (status) {
			logger.info(":: User " + userBean.getSsoId() +"updated successfully");
			responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_SUCCESS_CODE);
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "User updated successfully");
			return new ResponseEntity<String>(responseObj.toString(), HttpStatus.OK);
		}

		else {
			logger.info(":: Error Updating User " + userBean.getSsoId());
			responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_ERROR_CODE);
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "Error updating user");
			return new ResponseEntity<String>(responseObj.toString(), HttpStatus.OK);
		}

	}

	@RequestMapping(value = { "/deleteUser/" }, method = RequestMethod.DELETE)

	public ResponseEntity<String> deleteUser(@RequestParam("ssoId") String ssoId) {

		logger.info(":: UserRestController : deleteUser::Deleting user with ssoId " + ssoId);
		boolean status = false;
		JsonObject responseObj = new JsonObject();
		try {
			if (userService.findBySSO(ssoId) != null) {
				status = userService.deleteUserBySSO(ssoId);
			}
		} catch (Exception e) {

			logger.error("Error deleting user:" + e.getMessage());
			responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_ERROR_CODE);
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "User deleted successfully");
			return new ResponseEntity(responseObj.toString(), HttpStatus.OK);
		}
		if (status) {
			logger.info(":: User " + ssoId +" deleted successfully");
			responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_SUCCESS_CODE);
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "User deleted successfully");
			return new ResponseEntity(responseObj.toString(), HttpStatus.OK);
			// You many decide to return HttpStatus.NOT_FOUND
		} else {

			logger.info(":: Error deleting User " + ssoId);
			responseObj.addProperty(IApplicationConstants.REST_STATUS, IApplicationConstants.STATUS_ERROR_CODE);
			responseObj.addProperty(IApplicationConstants.REST_MESSAGE, "Error deleting user");
			return new ResponseEntity(responseObj.toString(), HttpStatus.OK);
		}

	}
}