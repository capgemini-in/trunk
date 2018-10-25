package com.capgemini.webapp.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.webapp.common.constants.IApplicationConstants;
import com.capgemini.webapp.dao.api.UserDao;
import com.capgemini.webapp.dao.api.entity.User;
import com.capgemini.webapp.dao.api.entity.UserInfo;
import com.capgemini.webapp.dao.api.entity.UserProfile;
import com.capgemini.webapp.security.constants.AuthenticationConstants;
import com.capgemini.webapp.service.api.EmailService;
import com.capgemini.webapp.service.api.UserService;
import com.capgemini.webapp.service.api.model.EmailModel;
import com.capgemini.webapp.service.api.model.UserInfoModel;
import com.capgemini.webapp.service.api.model.UserModel;
import com.capgemini.webapp.service.api.model.UserProfileModel;


/**
 * @author awarhoka
 *
 */
@Service
@Transactional

public class UserServiceImpl implements UserService {
	
	public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private Environment env;

	@Autowired
	private UserDao dao;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	public UserModel findById(int id) {
		UserModel usermodel = null;
		User user = null;
		String authentication = env.getRequiredProperty(AuthenticationConstants.AUTHENTICATION);
		if (authentication.equalsIgnoreCase(AuthenticationConstants.LDAP_AUTH)) {
			user = dao.findLdapUserBySSO(String.valueOf(id));
		} else {
			user = dao.findById(id);
		}
		if (user != null) {
			usermodel = new DozerBeanMapper().map(user, UserModel.class);
		}
		return usermodel;
	}

	public UserModel findBySSO(String sso) {
		UserModel usermodel = null;
		User user = null;
		user = dao.findBySSO(sso);
		if (user != null) {
			usermodel = new DozerBeanMapper().map(user, UserModel.class);
		}
		return usermodel;
	}
	
	public UserModel findBySSOEmail(String sso,String email) {
		UserModel usermodel = null;
		User user = null;
		user = dao.findBySSOEmail(sso,email);
		if (user != null) {
			usermodel = new DozerBeanMapper().map(user, UserModel.class);
		}
		return usermodel;
	}

	@Transactional
	public boolean saveUser(UserModel userModel) {		
		
		boolean created=false;
		try {
			
			//Checks if SSOID is null, if null then generates SSOID
			if(userModel.getSsoId()==null) {
				//String ssoId= createSSOID(userModel.getFirstName(), userModel.getLastName());
				//String ssoId=userModel.getMobileNumber();
				if(userModel.getMobileNumber() !=null) {
								
					userModel.setSsoId(userModel.getMobileNumber());
				    userModel.setPassword("guest123");
				}
				
			}
			 if (isUserSSOUnique(userModel.getId(), userModel.getSsoId())) {
				
					//userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
					User userEntity = new DozerBeanMapper().map(userModel, User.class);
					userEntity.setPassword(passwordEncoder.encode(userModel.getPassword()));
					dao.save(userEntity);
					created=true;
					userModel.setId(userEntity.getId());
					sendEmailNotification(userModel);
					
			}
				
				
		}catch(Exception e) {
			
			logger.error("Error updating user entity:"+e.getMessage());
			created=false;
		}
		return created;
		
		
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate
	 * update explicitly. Just fetch the entity from db and update it with proper
	 * values within transaction. It will be updated in db once transaction ends.
	 */
	public boolean updateUser(UserModel userModel) {
		logger.debug("Updating User Details");
		boolean status=false;
		try {
			
			User userEntity = new DozerBeanMapper().map(userModel, User.class);
			User entity = dao.findBySSO(userEntity.getSsoId());
			//User entity = dao.findById(userEntity.getId());
			if (entity != null) {
				
				entity.setSsoId(userModel.getSsoId());
				if (!userModel.getPassword().equals(entity.getPassword())) {
					entity.setPassword(passwordEncoder.encode(userModel.getPassword()));
				}
				entity.setFirstName(userModel.getFirstName());
				entity.setLastName(userModel.getLastName());
				entity.setEmail(userModel.getEmail());
				
				Set<UserProfile> userProfileEntity = getUpdatedUserProfiles(userModel.getUserProfiles(), UserProfile.class);
				entity.setUserProfiles(userProfileEntity);
				status=true;
				
			}
		}catch(Exception e) {
			
			logger.error("Exception upating user bean:"+ e.getMessage());
			status=false;		
			
		}
		return status;
		
	}
	
	

	/**
	 * 
	 * @param userProfiles
	 * @param toClass
	 * @return
	 */
	private Set<UserProfile> getUpdatedUserProfiles(Set<UserProfileModel> userProfiles, Class<UserProfile> toClass) {
		return userProfiles.stream().map(from -> new DozerBeanMapper().map(from, toClass)).collect(Collectors.toSet());
	}

	public boolean deleteUserBySSO(String sso) {
		boolean isDeleted=false;
		try { 
			dao.deleteBySSO(sso);
			isDeleted=true;
			
		}catch(Exception e) {
			isDeleted=false;
			return isDeleted;
		}
		return isDeleted;
	}

	@Transactional(readOnly = true)
	public List<UserModel> findAllUsers() {
		List<UserModel> userlist = null;
		try {
			return this.mapList(dao.findAllUsers(), UserModel.class);
		//=this.mapList(dao.findAllUsers(), UserModel.class);
		}catch(Exception e) {
			logger.error("Error retrieving users:"+e.getMessage());
			return null;
		}
		
		/*String authentication = env.getRequiredProperty(AuthenticationConstants.AUTHENTICATION);
		if (authentication.equalsIgnoreCase(AuthenticationConstants.LDAP_AUTH)) {
			userlist = this.mapList(dao.findAllLdapUsers(), UserModel.class);
		} else {
			userlist = this.mapList(dao.findAllUsers(), UserModel.class);
		}*/
		
	}

	/* this is Mapper for List */
	private List<UserModel> mapList(List<User> fromList, final Class<UserModel> toClass) {
		return fromList.stream().map(from -> new DozerBeanMapper().map(from, toClass)).collect(Collectors.toList());
	}

	public boolean isUserSSOUnique(Integer id, String sso) {
		UserModel userModel = findBySSO(sso);
		//return userModel == null;
		return (userModel == null || ((id != null) && (userModel.getId() == id)));
	}

	

	
	@Override
	public List<UserModel> getallProfile() {
		/*List<UserModel> userlist = null;
		String authentication = env.getRequiredProperty(AuthenticationConstants.AUTHENTICATION);
		if (authentication.equalsIgnoreCase(AuthenticationConstants.LDAP_AUTH)) {
			userlist = this.mapList(dao.findLdapUserGroup(), UserModel.class);
		} else {
			userlist = this.mapList(dao.UserProfileType(), UserModel.class);
		}*/
		return this.mapList(dao.UserProfileType(), UserModel.class);
	}

	@Override
	@Transactional
	public void updateList(List<UserInfoModel> p) throws Exception {
		List<UserInfo> userlist = this.mapuserInfoList(p, UserInfo.class);
		dao.updateUserInfos(userlist);
	}

	private List<UserInfo> mapuserInfoList(List<UserInfoModel> fromList, final Class<UserInfo> toClass) {
		return fromList.stream().map(from -> new DozerBeanMapper().map(from, toClass)).collect(Collectors.toList());
	}
	
	@Override
	public void uploadUser(List<UserModel> lstUser) throws Exception {
		List<User> userlist = this.mapUploadUserToList(lstUser, User.class);
		dao.uploadUsers(userlist);		
	}

	private List<User> mapUploadUserToList(List<UserModel> fromList, Class<User> toClass) {
		return fromList.stream().map(from -> new DozerBeanMapper().map(from, toClass)).collect(Collectors.toList());
	} 
	
	/**
	 * Function to generate ssoid
	 */
	public String  createSSOID (String firstName, String lastName) {
		int counter=4;
		int inc=0;
		StringBuffer buffer=new StringBuffer();
		do {
			
			buffer=new StringBuffer();			
			if(firstName.length()>=counter+inc) 
			{
			
				buffer.append(firstName.substring(0,counter+inc));			
				buffer.append(lastName.substring(0,counter-inc));
				
				System.out.println("SSOID:" + buffer.toString());
				
				UserModel user=findBySSO(buffer.toString());
				if(user==null)
					return  buffer.toString();
				
				 
				inc++;
			}	
		}while(inc<3);
			
		return buffer.toString();
	}
	
	
	
	public String getUserDetail(UserModel userModel) {
		
		String status="";
		UserModel newUser=null;
		try {
			
			if(userModel.getSsoId()==null) {
				
				if(userModel.getMobileNumber() !=null) {								
						userModel.setSsoId(userModel.getMobileNumber());
					    userModel.setPassword("guest123");
				}
			}			
			newUser =	findBySSOEmail(userModel.getSsoId(), userModel.getEmail());
			
			if(newUser==null) {
					
					//userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
					User userEntity = new DozerBeanMapper().map(userModel, User.class);
					userEntity.setPassword(passwordEncoder.encode(userModel.getPassword()));
					dao.save(userEntity);
					status=IApplicationConstants.USER_CREATED;;
					userModel.setId(userEntity.getId());
					sendEmailNotification(userModel);
					
			} else {
				//check if FN and LN matches with user entity from db
				if(userModel.getFirstName().equals(newUser.getFirstName()) && userModel.getLastName().equals(newUser.getLastName())) {
					
					status=IApplicationConstants.USER_CREATED;
					userModel.setId(newUser.getId());
							
				}else {
					status=IApplicationConstants.DUPLICATE_USER;
				}
				
			}
			}catch(Exception e) {
				
			}
			
		
		return status;
	}
	
	private void sendEmailNotification(UserModel newUser) {
		EmailModel emailModel=new EmailModel();
		emailModel.setToEmail(newUser.getEmail());
		emailModel.setFromEmail("admin@cgmotors.com");
		emailModel.setCcEmail("vipul.satpute@capgemini.com");
		emailModel.setSubject("Your CGMotors Account Created");
		emailModel.setContent("Hi " + newUser.getFirstName() +				
				"\n Welcome to CGMotors. Your account has been created."
				+ " \n \n Following are the details:\n  Username: "+newUser.getSsoId() +"\n Password: "+ newUser.getPassword() + "\n \n \n Regards \n CG Motors");
		
		emailService.sendEmail(emailModel);
		System.out.println("Email send");
		
	}
	
	
	
	
}
