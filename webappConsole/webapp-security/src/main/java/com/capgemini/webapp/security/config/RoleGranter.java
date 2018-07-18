package com.capgemini.webapp.security.config;

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.stereotype.Service;

import com.capgemini.webapp.ldap.repository.GroupRepository;
import com.capgemini.webapp.ldap.repository.UserRepository;
import com.capgemini.webapp.security.constants.AuthenticationConstants;
import com.capgemini.webapp.service.api.UserService;
import com.capgemini.webapp.service.api.model.UserModel;
import com.capgemini.webapp.service.api.model.UserProfileModel;

@Service("roleGranter")
@PropertySource("classpath:/config/ldap/ldap_details.properties")
public class RoleGranter implements AuthorityGranter {

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	UserService userService;

	@Autowired
	private Environment env;

	@Override
	public Set<String> grant(Principal principal) {

		String authentication = env.getRequiredProperty(AuthenticationConstants.AUTHENTICATION);

		Set<String> roles = new HashSet();
		
		if (authentication.equalsIgnoreCase(AuthenticationConstants.LDAP_AUTH)) {
			//List<LdapUser> ldapUser = userRepository.findByName(principal.getName());
			List<LdapGroup> groups = groupRepository.findAll();
			System.out.println(" Groups size : " + groups.size());
			for (LdapGroup group : groups) {
				System.out.println(" Gorup Name : " + group.getName());
				Set<Name> memberSet = group.getMembers();
				for (Name userName : memberSet) {
					String role_prefix = "ROLE_";
					String uid = userName.toString();
					if (principal.getName().equals(uid)) {
						role_prefix = role_prefix + group.getName();
						Set<String> groupSet = Collections.singleton("ROLE_"+group.getName());
						roles.addAll(groupSet);
					}
				}
			}
		}
		
		if (authentication.equalsIgnoreCase(AuthenticationConstants.DB_AUTH)) {

			UserModel userRoles = userService.findBySSO(principal.getName());
			System.out.println(" User Roles :"+userRoles.getUserProfiles());
			for(UserProfileModel profile : userRoles.getUserProfiles() ) {
				
				System.out.println(" Role :"+profile.getType());
				Set<String> groupSet =  Collections.singleton("ROLE_"+profile.getType());
				roles.addAll(groupSet);
			}
			
			/*if (principal.getName().equals("vipsatpu")) {
				return Collections.singleton("ROLE_ADMIN");
			}
			if (principal.getName().equals("pallapat")) {
				return Collections.singleton("ROLE_DBA");
			} 
			if (principal.getName().equals("divvyas")) {
				return Collections.singleton("ROLE_USER");
			}
			else {
				return Collections.singleton("ENDUSER");
			}*/
		}

		return roles;

	}
}
