package com.capgemini.webapp.spring.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.capgemini.webapp.service.api.UserProfileService;
import com.capgemini.webapp.service.api.UserService;
import com.capgemini.webapp.service.api.model.UserModel;

@RestController
@RequestMapping("/ldap/api")
public class LdapRestController {

	@Autowired
	UserService userService;

	@Autowired
	UserProfileService userProfileService;

	// -----------Retrieve All Users------------

	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<UserModel>> listAllUsers() {
		List<UserModel> users = userService.findAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<UserModel>>(users, HttpStatus.OK);
	}
	
	//-------------------Create a UserModel--------------------------------------------------------
    
    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<String> createUser(@RequestBody UserModel userModel,    UriComponentsBuilder ucBuilder) {
        System.out.println("Creating UserModel " + userModel.getSsoId());
 
        //if (userService.isUserExist(userModel)) {
        if(userService.isUserSSOUnique(userModel.getId(), userModel.getSsoId()))
        {
            System.out.println("A UserModel with name " + userModel.getSsoId() + " already exist");
            return new ResponseEntity<String>("registration",HttpStatus.CONFLICT);
        }
 
        userService.saveUser(userModel);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/ldap/user/{id}").buildAndExpand(userModel.getId()).toUri());
        return new ResponseEntity<String>("registrationsuccess", HttpStatus.CREATED);
    }
    
  //-------------------Retrieve Single UserModel--------------------------------------------------------
    
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> getUser(@PathVariable("id") int id) {
        System.out.println("Fetching UserModel with id " + id);
        UserModel userModel = userService.findById(id);
        if (userModel == null) {
            System.out.println("UserModel with id " + id + " not found");
            return new ResponseEntity<UserModel>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserModel>(userModel, HttpStatus.OK);
    }

}
