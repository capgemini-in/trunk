package com.capgemini.webapp.spring.controller.user;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.capgemini.webapp.dao.api.entity.FileBucket;
import com.capgemini.webapp.service.api.UserProfileService;
import com.capgemini.webapp.service.api.UserService;
import com.capgemini.webapp.service.api.model.UserInfoModel;
import com.capgemini.webapp.service.api.model.UserModel;
import com.capgemini.webapp.service.api.model.UserProfileModel;
import com.capgemini.webapp.spring.controller.BaseController;

@Controller
@RequestMapping("/")
@SessionAttributes("roles")

public class UserController extends BaseController {

	@Autowired
	UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	UserProfileService userProfileService;

	public static final String REST_SERVICE_URI = "http://LIN17000289:8083/pocwebapp";

	private static String UPLOAD_LOCATION = "D:\\log";

	// public static final String REST_SERVICE_URI =
	// "http://10.76.132.95:8280/UserManagement/1.0.0" ;

	/**
	 * This method will list all existing users.
	 */
	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public String listUsers(ModelMap model) {

		List<UserModel> users = new ArrayList();
		try {
			
			 URI uri = new URI("http://10.48.124.83:8280/UserManagement/1.0.0"+"/api/user/");
			//URI uri = new URI("http://localhost:8082/pocwebapp" + "/api/user/");
			RestTemplate restTemplate = new RestTemplate();
			List<LinkedHashMap<String, Object>> usersMap = restTemplate.getForObject(uri, List.class);
			if (usersMap != null) {
				for (LinkedHashMap<String, Object> map : usersMap) {
					System.out.println("User : id=" + map.get("id") + ", ssoId=" + map.get("ssoId") + ", firstName="
							+ map.get("firstName") + ", lastName=" + map.get("lastName"));
					;
					UserModel user = new UserModel();
					user.setSsoId(map.get("ssoId").toString());
					user.setFirstName(map.get("firstName").toString());
					user.setLastName(map.get("lastName").toString());
					user.setEmail(map.get("email").toString());
					if (map.get("id") != null && map.get("id") != "") {
						user.setId(Integer.valueOf(map.get("id").toString()));
					}
					users.add(user);
				}
			} else {
				// System.out.println("No user exist----------");
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// List<UserModel> users = userService.findAllUsers();
		// return Response.ok(new
		// Gson().toJson(users)).type(MediaType.APPLICATION_JSON).build();
		/*
		 * ObjectMapper mapperObj = new ObjectMapper(); String jsonData = null; try {
		 * jsonData = mapperObj.writeValueAsString(users); } catch
		 * (JsonProcessingException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		model.addAttribute("users", users);
		model.addAttribute("loggedinuser", super.getPrincipal());
		return "userslist";
	}

	/**
	 * This method will provide the medium to add a new user.
	 */
	@RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
	public String newUser(ModelMap model) {
		UserModel user = new UserModel();
		model.addAttribute("user", user);
		model.addAttribute("edit", false);
		model.addAttribute("loggedinuser", super.getPrincipal());
		return "registration";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * saving user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
	public String saveUser(@Valid UserModel user, BindingResult result, ModelMap model) {

		/*
		 * try { URI uri = new URI(REST_SERVICE_URI+"/api/user/"); RestTemplate
		 * restTemplate = new RestTemplate();
		 * 
		 * restTemplate.postForLocation("http://localhost:8082/pocwebapp/api/userPost/",
		 * user, UserModel.class);
		 * 
		 * } catch (URISyntaxException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		if (result.hasErrors()) {
			List<UserModel> users = userService.findAllUsers();
			model.addAttribute("users", users);
			return "registration";
		}

		/*
		 * Preferred way to achieve uniqueness of field [sso] should be implementing
		 * custom @Unique annotation and applying it on field [sso] of Model class
		 * [UserModel].
		 * 
		 * Below mentioned peace of code [if block] is to demonstrate that you can fill
		 * custom errors outside the validation framework as well while still using
		 * internationalized messages.
		 * 
		 */
		if (!userService.isUserSSOUnique(user.getId(), user.getSsoId())) {
			FieldError ssoError = new FieldError("user", "ssoId", messageSource.getMessage("non.unique.ssoId",
					new String[] { user.getSsoId() }, Locale.getDefault()));
			result.addError(ssoError);
			return "registration";
		}

		userService.saveUser(user);

		model.addAttribute("success",
				"UserModel " + user.getFirstName() + " " + user.getLastName() + " registered successfully");
		model.addAttribute("loggedinuser", super.getPrincipal());
		// return "success";
		return "registrationsuccess";
	}

	/**
	 * This method will provide the medium to update an existing user.
	 */
	@RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.GET)
	public String editUser(@PathVariable String ssoId, ModelMap model) {
		UserModel user = userService.findBySSO(ssoId);
		model.addAttribute("user", user);
		model.addAttribute("edit", true);
		model.addAttribute("loggedinuser", super.getPrincipal());
		return "registration";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * updating user in database. It also validates the user input
	 */

	@RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("user") UserModel user, BindingResult result, ModelMap model,
			@PathVariable String ssoId) {

		if (result.hasErrors()) {
			return "registration";
		}

		userService.updateUser(user);

		model.addAttribute("success",
				"UserModel " + user.getFirstName() + " " + user.getLastName() + " updated successfully");
		model.addAttribute("loggedinuser", super.getPrincipal());
		return "registrationsuccess";
	}

	/**
	 * This method will delete an user by it's SSOID value.
	 */
	@RequestMapping(value = { "/delete-user-{ssoId}" }, method = RequestMethod.GET)
	public String deleteUser(@PathVariable String ssoId) {
		userService.deleteUserBySSO(ssoId);
		return "redirect:/list";
	}

	/**
	 * This method will provide UserProfileModel list to views
	 */
	@ModelAttribute("roles")
	public List<UserProfileModel> initializeProfiles() {
		return userProfileService.findAll();
	}

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String home(ModelMap model, Principal principal) {
		String name = principal.getName(); // get logged in username
		model.addAttribute("name", name);

		System.out.println("This is the username" + name);
		return "home";
	}

	/**
	 * This method will list all existing users.
	 */
	@RequestMapping(value = { "/userpermission" }, method = RequestMethod.GET)
	public String listRoles(ModelMap model) {

		List<UserModel> usrprofile = userService.getallProfile();
		List<String[]> userP = new ArrayList<String[]>();
		for (UserModel user : usrprofile) {
			String[] permission = new String[2];
			permission[0] = user.getFirstName();
			permission[1] = user.getUserProfiles().toString();
			userP.add(permission);
		}
		System.out.println(usrprofile);
		model.addAttribute("users", userP);
		model.addAttribute("loggedinuser", super.getPrincipal());
		return "userScreenPermission";
	}

	/**
	 * This method will return the usermenu right page
	 */
	@RequestMapping(value = { "/usermenuright" }, method = RequestMethod.GET)
	public String menuRight(ModelMap model) {

		return "userMenuRights";
	}

	/**
	 * This method will provide the medium to add a new role.
	 */
	@RequestMapping(value = { "/newRole" }, method = RequestMethod.GET)
	public String newRole(ModelMap model) {
		return "newRole";
	}

	// to open upload page

	@RequestMapping(value = "/singleUpload", method = RequestMethod.GET)
	public String getSingleUploadPage(ModelMap model) {
		FileBucket fileModel = new FileBucket();
		model.addAttribute("fileBucket", fileModel);
		return "redirect:/singleFileUploader";

	}

	@RequestMapping(value = { "/singleFileUploader" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String singleFileUploader(@Valid FileBucket fileBucket, BindingResult result, ModelMap mode,
			ModelMap model) {
		return "singleFileUploader";
	}

	@SuppressWarnings("resource")
	@RequestMapping(value = "/singleUpload", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public String singleFileUpload(@Valid FileBucket fileBucket, BindingResult result, ModelMap model)

	{
		List<UserModel> users = userService.findAllUsers();
		List<String> ssoId = new ArrayList<>();
		// getting ssoID
		for (int j = 0; j < users.size(); j++) {
			ssoId.add(users.get(j).getSsoId());
		}

		if (result.hasErrors()) {
			System.out.println("validation errors");
			return "redirect:/singleFileUploader";
			/* return "singleFileUploader"; */
		} else {
			try {
				System.out.println("Fetching file");
				MultipartFile multipartFile = fileBucket.getFile();
				// Now do something with file...
				FileCopyUtils.copy(fileBucket.getFile().getBytes(),
						new File(UPLOAD_LOCATION + fileBucket.getFile().getOriginalFilename()));
				String fileName = multipartFile.getOriginalFilename();
				model.addAttribute("fileName", fileName);
				System.out.println("This is file name" + fileName);
				// to save the file
				InputStream inputStream = null;
				OutputStream outputStream = null;
				inputStream = multipartFile.getInputStream();
				File newFile = new File(UPLOAD_LOCATION + "\\" + fileName);
				System.out.println("This is the location" + newFile);
				if (!newFile.exists()) {
					newFile.createNewFile();
				}
				outputStream = new FileOutputStream(newFile);
				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}

				// this is excel upload
				List<UserInfoModel> lstUser = new ArrayList<>();

				int i = 1;
				Workbook workbook;

				workbook = WorkbookFactory.create(new File(UPLOAD_LOCATION + "\\" + fileName));
				Sheet sheet = workbook.getSheetAt(0);
				Row row;
				while (i <= sheet.getLastRowNum()) {
					UserInfoModel user = new UserInfoModel();
					row = sheet.getRow(i++);
					user.setFirstName(row.getCell(0).getStringCellValue());
					user.setLastName(row.getCell(1).getStringCellValue());
					user.setUsername(row.getCell(2).getStringCellValue());
					user.setRole(row.getCell(3).getStringCellValue());
					user.setPassword(row.getCell(4).getStringCellValue());
					if (!ssoId.contains(user.getUsername())) {
						lstUser.add(user);
					}
				}
				System.out.println(lstUser);
				workbook.close();
				userService.updateList(lstUser);
				model.addAttribute("lstUser", lstUser);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "home";
		}
	}

	@SuppressWarnings("resource")
	@RequestMapping(value = "/uploadUsers", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public String uploadUsers(@Valid FileBucket fileBucket, BindingResult result, ModelMap model)

	{
		List<UserModel> users = userService.findAllUsers();
		List<String> ssoId = new ArrayList<>();
		// getting ssoID
		for (int j = 0; j < users.size(); j++) {
			ssoId.add(users.get(j).getSsoId());
		}

		if (result.hasErrors()) {
			System.out.println("validation errors");
			return "redirect:/singleFileUploader";
		} else {
			try {
				System.out.println("Fetching file");
				MultipartFile multipartFile = fileBucket.getFile();
				// Now do something with file...
				FileCopyUtils.copy(fileBucket.getFile().getBytes(),
						new File(UPLOAD_LOCATION + fileBucket.getFile().getOriginalFilename()));
				String fileName = multipartFile.getOriginalFilename();
				model.addAttribute("fileName", fileName);
				System.out.println("This is file name" + fileName);
				// to save the file
				InputStream inputStream = null;
				OutputStream outputStream = null;
				inputStream = multipartFile.getInputStream();
				File newFile = new File(UPLOAD_LOCATION + "\\" + fileName);
				System.out.println("This is the location" + newFile);
				if (!newFile.exists()) {
					newFile.createNewFile();
				}
				outputStream = new FileOutputStream(newFile);
				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}

				// this is excel upload
				List<UserModel> lstUser = new ArrayList<>();

				int i = 1;
				Workbook workbook;

				workbook = WorkbookFactory.create(new File(UPLOAD_LOCATION + "\\" + fileName));
				Sheet sheet = workbook.getSheetAt(0);
				Row row;
				while (i <= sheet.getLastRowNum()) {
					UserModel user = new UserModel();
					row = sheet.getRow(i++);
					user.setSsoId(row.getCell(0).getStringCellValue());
					user.setPassword(passwordEncoder.encode(row.getCell(1).getStringCellValue()));
					user.setFirstName(row.getCell(2).getStringCellValue());
					user.setLastName(row.getCell(3).getStringCellValue());
					user.setEmail(row.getCell(4).getStringCellValue());
					UserProfileModel profile = new UserProfileModel();
					String role = row.getCell(5).getStringCellValue();
					if("USER".equalsIgnoreCase(role)) {
						profile.setId(1);
					}
					if("ADMIN".equalsIgnoreCase(role)) {						
						profile.setId(2);
					}
					if("DBA".equalsIgnoreCase(role)) {
						profile.setId(3);
					}
					profile.setType(row.getCell(5).getStringCellValue());
					user.getUserProfiles().add(profile);
					if (!ssoId.contains(user.getSsoId())) {
						lstUser.add(user);
					}
				}
				System.out.println(lstUser);
				workbook.close();
				userService.uploadUser(lstUser);
				model.addAttribute("lstUser", lstUser);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "home";
		}
	}

}