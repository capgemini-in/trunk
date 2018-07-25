package com.capgemini.webapp.spring.controller.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.webapp.service.api.UserProfileService;
import com.capgemini.webapp.service.api.UserService;
import com.capgemini.webapp.service.api.model.UserModel;

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
	 * @return
	 */
	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<UserModel>> listAllUsers() {

		List<UserModel> users = userService.findAllUsers();

		if (users.isEmpty()) {

			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<UserModel>>(users, HttpStatus.OK);
	}

	
	@RequestMapping(value = { "/newUser/" }, method = RequestMethod.POST)
	public ResponseEntity<String> newUser( @RequestBody  UserModel  userBean) {

		logger.info("New User::POST::Creating new user::");
		
		boolean isCreated=false;
		
		if(userBean!=null) {
			
			if (userService.isUserSSOUnique(userBean.getId(), userBean.getSsoId())) { 
			
					isCreated =  userService.saveUser(userBean);
			}
		} 
		if (isCreated)
			return new ResponseEntity<String>("success",HttpStatus.OK);
		else
			return new ResponseEntity<String>("error", HttpStatus.OK);
	}
	
/*	*//**
	 * This method creates new user in database
	 * 
	 * @param userModel
	 * @param ucBuilder
	 * @return
	 *//*
	@RequestMapping(value = "/createUser/", method = RequestMethod.POST)
	public ResponseEntity<String> createUser(@RequestBody UserModel userModel, UriComponentsBuilder ucBuilder) {

		logger.info("createUser::Creating new user- " + userModel.getSsoId());
		if (!userService.isUserSSOUnique(userModel.getId(), userModel.getSsoId())) {

			logger.debug("createUser::User with name " + userModel.getSsoId() + " already exist");
			return new ResponseEntity<String>("registration", HttpStatus.CONFLICT);

		}

		boolean isCreated=false;
		if(userModel!=null) {
			
			isCreated =  userService.saveUser(userModel);
		} 
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(userModel.getId()).toUri());
		
		if (isCreated)
			return new ResponseEntity<String>("registrationsuccess",HttpStatus.CREATED);
		else
			return new ResponseEntity<String>("error", HttpStatus.OK);
	
		
	}*/

	@RequestMapping(value = { "/getUser/" }, method = RequestMethod.GET)
	@CrossOrigin(origins = "*")
	public ResponseEntity<UserModel> getUser(@RequestParam("ssoId") String ssoId) {

		logger.info("getProduct::Retrieving user with ssoId " + ssoId);

		UserModel user = userService.findBySSO(ssoId);
		if (user == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}

		return new ResponseEntity<UserModel>(user, HttpStatus.OK);

	}

	/**
	 * Updates existing product
	 * @param userBean
	 * @return
	 */
	@RequestMapping(value = "/editUser/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateUser( @RequestBody  UserModel userBean) {
		
	
			boolean status=	userService.updateUser(userBean);
			if(status) {
				return new ResponseEntity<String>("success", HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("error", HttpStatus.OK);
			}
			
				
	}
	
		

	@RequestMapping(value = { "/deleteUser/" }, method = RequestMethod.DELETE)
	
	public ResponseEntity<String> deleteUser(@RequestParam("ssoId") String ssoId) {

		logger.info("deleteUser::Deleting user with ssoId " + ssoId);
		boolean status=false;
		if(userService.findBySSO(ssoId)!=null) {
		 status= userService.deleteUserBySSO(ssoId);
		}
		if (status) {
			return new ResponseEntity("success", HttpStatus.OK);
			// You many decide to return HttpStatus.NOT_FOUND
		}else {
			return new ResponseEntity("error", HttpStatus.OK);
		}

	}
	/*
	 * 
	 * //-------------------Retrieve All
	 * Users--------------------------------------------------------
	 * 
	 * @RequestMapping(value = "/user/", method = RequestMethod.GET) public
	 * ResponseEntity<List<UserModel>> listAllUsers() { List<UserModel> userModels =
	 * userService.findAllUsers(); if(userModels.isEmpty()){ return new
	 * ResponseEntity<List<UserModel>>(HttpStatus.NO_CONTENT);//You many decide to
	 * return HttpStatus.NOT_FOUND } return new
	 * ResponseEntity<List<UserModel>>(userModels, HttpStatus.OK); }
	 * 
	 * 
	 * 
	 * //-------------------Retrieve Single
	 * UserModel--------------------------------------------------------
	 * 
	 * @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces =
	 * MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<UserModel>
	 * getUser(@PathVariable("id") int id) {
	 * System.out.println("Fetching UserModel with id " + id); UserModel userModel =
	 * userService.findById(id); if (userModel == null) {
	 * System.out.println("UserModel with id " + id + " not found"); return new
	 * ResponseEntity<UserModel>(HttpStatus.NOT_FOUND); } return new
	 * ResponseEntity<UserModel>(userModel, HttpStatus.OK); }
	 * 
	 * 
	 * 
	 * //-------------------Create a
	 * UserModel--------------------------------------------------------
	 * 
	 * @RequestMapping(value = "/user/", method = RequestMethod.POST) public
	 * ResponseEntity<Void> createUser(@RequestBody UserModel userModel,
	 * UriComponentsBuilder ucBuilder) { System.out.println("Creating UserModel " +
	 * userModel.getSsoId());
	 * 
	 * if (userService.isUserExist(userModel)) {
	 * System.out.println("A UserModel with name " + userModel.getSsoId() +
	 * " already exist"); return new ResponseEntity<Void>(HttpStatus.CONFLICT); }
	 * 
	 * userService.saveUser(userModel);
	 * 
	 * HttpHeaders headers = new HttpHeaders();
	 * headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(userModel.
	 * getId()).toUri()); return new ResponseEntity<Void>(headers,
	 * HttpStatus.CREATED); }
	 * 
	 * 
	 * 
	 * //------------------- Update a UserModel
	 * --------------------------------------------------------
	 * 
	 * @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT) public
	 * ResponseEntity<UserModel> updateUser(@PathVariable("id") int id, @RequestBody
	 * UserModel userModel) { System.out.println("Updating UserModel " + id);
	 * 
	 * UserModel currentUser = userService.findById(id);
	 * 
	 * if (currentUser==null) { System.out.println("UserModel with id " + id +
	 * " not found"); return new ResponseEntity<UserModel>(HttpStatus.NOT_FOUND); }
	 * 
	 * //currentUser.setUsername(userModel.getSsoId());
	 * //currentUser.setAddress(userModel.getAddress());
	 * //currentUser.setEmail(userModel.getEmail());
	 * 
	 * userService.updateUser(currentUser); return new
	 * ResponseEntity<UserModel>(currentUser, HttpStatus.OK); }
	 * 
	 * 
	 * 
	 * //------------------- Delete a UserModel
	 * --------------------------------------------------------
	 * 
	 * @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE) public
	 * ResponseEntity<UserModel> deleteUser(@PathVariable("id") int id) {
	 * System.out.println("Fetching & Deleting UserModel with id " + id);
	 * 
	 * UserModel userModel = userService.findById(id); if (userModel == null) {
	 * System.out.println("Unable to delete. UserModel with id " + id +
	 * " not found"); return new ResponseEntity<UserModel>(HttpStatus.NOT_FOUND); }
	 * 
	 * //userService.deleteUserById(id); return new
	 * ResponseEntity<UserModel>(HttpStatus.NO_CONTENT); }
	 * 
	 * 
	 * 
	 * //------------------- Delete All Users
	 * --------------------------------------------------------
	 * 
	 * @RequestMapping(value = "/user/", method = RequestMethod.DELETE) public
	 * ResponseEntity<UserModel> deleteAllUsers() {
	 * System.out.println("Deleting All Users");
	 * 
	 * //userService.deleteAllUsers(); return new
	 * ResponseEntity<UserModel>(HttpStatus.NO_CONTENT); }
	 */

}