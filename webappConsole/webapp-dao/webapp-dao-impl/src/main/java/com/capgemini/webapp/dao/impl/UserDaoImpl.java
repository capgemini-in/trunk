package com.capgemini.webapp.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Name;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.capgemini.webapp.common.utils.LoggingMessages;
import com.capgemini.webapp.dao.api.UserDao;
import com.capgemini.webapp.dao.api.entity.LdapUser;
import com.capgemini.webapp.dao.api.entity.User;
import com.capgemini.webapp.dao.api.entity.UserInfo;
import com.capgemini.webapp.dao.api.entity.UserProfile;
import com.capgemini.webapp.dao.impl.config.mybatis.mapper.usermapper.UserMapper;
import com.capgemini.webapp.ldap.repository.GroupRepository;
import com.capgemini.webapp.ldap.repository.UserRepository;
import com.capgemini.webapp.security.config.LdapGroup;
import com.capgemini.webapp.security.config.LdapUserModel;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

	static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	@Autowired
	UserMapper mapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GroupRepository groupRepository;

	public User findById(int id) {
		logger.info(LoggingMessages.getMessage("USER.FIND"), "ID",id);
		User user = getByKey(id);
		if (user != null) {
			Hibernate.initialize(user.getUserProfiles());
		}
		return user;
	}

	public User findBySSO(String sso) {
		logger.info(LoggingMessages.getMessage("USER.FIND"), "SSO",sso);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("ssoId", sso));
		User user = (User) crit.uniqueResult();
		// User user= mapper.retrieveUserBySSOID(sso);
		// user.setUserProfiles(mapper.getUserProfile(user.getId()));
		if (user != null) {
			Hibernate.initialize(user.getUserProfiles());
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() {
		logger.info(LoggingMessages.getMessage("USER.ALL"));
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("firstName"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid duplicates.
		List<User> users = (List<User>) criteria.list();

		// No need to fetch userProfiles since we are not showing them on list page. Let
		// them lazy load.
		// Uncomment below lines for eagerly fetching of userProfiles if you want.
		/*
		 * for(User user : users){ Hibernate.initialize(user.getUserProfiles()); }
		 */
		return users;
	}

	public User save(User user) {
		return saveUser(user);
	}

	public User saveUser(User user) {
		logger.info(LoggingMessages.getMessage("USER.CREATE"),user.getSsoId());
		getSession().save(user);
		logger.info(LoggingMessages.getMessage("USER.SAVED"),user.getSsoId(),user.getId());
		return user;
	}

	public User findUserBySSOID(String ssoId) {
		// Query query =
		// getSession().getNamedNativeQuery(User.QUERY_FIND_USER_BY_SSOID);
		logger.info(LoggingMessages.getMessage("USER.FIND"), "SSO",ssoId);
		EntityManager em = Persistence.createEntityManagerFactory("JPA").createEntityManager();
		TypedQuery<User> namedQuery = em.createNamedQuery(User.QUERY_FIND_USER_BY_SSOID, User.class);
		namedQuery.setParameter("ssoId", ssoId.toLowerCase());
		User user = (User) namedQuery.getSingleResult();
		return user;
	}

	public void deleteBySSO(String sso) {
		logger.info(LoggingMessages.getMessage("USER.DELETE"), "SSO",sso);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("ssoId", sso));
		User user = (User) crit.uniqueResult();
		delete(user);
		logger.info(LoggingMessages.getMessage("USER.DELETED"),sso);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> UserProfileType() {
		logger.info(LoggingMessages.getMessage("USER.PROFILE"));
		org.hibernate.Session session = getSession();
		SQLQuery query = session.createSQLQuery(
				"SELECT s.sso_id , hp.user_profile_id, h.type FROM app_user s INNER JOIN app_user_user_profile hp on s.id = hp.user_id INNER JOIN user_profile h on hp.user_profile_id = h.id");
		List<Object[]> rows = query.list();
		List<User> userList = getListOfUsersWithRoles(rows);
		return userList;
	}

	/**
	 * 
	 * @param rows
	 * @return
	 */
	private List<User> getListOfUsersWithRoles(List<Object[]> rows) {

		logger.info(LoggingMessages.getMessage("USER.ROLES"));
		Map<String, User> userRoleMap = new HashMap();
		List<User> userList = new ArrayList();
		for (Object[] row : rows) {
			String name = String.valueOf(row[0]);
			String roleId = String.valueOf(row[1]);
			String roleName = String.valueOf(row[2]);
			if (userRoleMap.containsKey(name)) {
				User user = userRoleMap.get(name);
				UserProfile up = new UserProfile();
				up.setId(Integer.parseInt(roleId));
				up.setType(roleName);
				user.getUserProfiles().add(up);
			} else {
				User user = new User();
				user.setFirstName(name);
				UserProfile up = new UserProfile();
				up.setId(Integer.parseInt(roleId));
				up.setType(roleName);
				user.getUserProfiles().add(up);
				userRoleMap.put(name, user);
			}
		}
		return getListOfUsersFromMap(userRoleMap, userList);
	}

	/**
	 * 
	 * @param userRoleMap
	 * @param userList
	 * @return
	 */
	private List<User> getListOfUsersFromMap(Map<String, User> userRoleMap, List<User> userList) {
		for (Map.Entry<String, User> entry : userRoleMap.entrySet()) {
			userList.add(entry.getValue());
		}
		return userList;
	}

	@Override
	public void updateUserInfos(List<UserInfo> plstUserInfo) {
		org.hibernate.Session session = getSession();
		if (plstUserInfo != null && plstUserInfo.size() > 0) {
			for (UserInfo entity : plstUserInfo) {
				session.persist(entity);
			}
		}
	}

	@Override
	public List<User> findAllLdapUsers() {
		logger.info(LoggingMessages.getMessage("LDAP.USER.ALL"));
		List<LdapUserModel> ldapUserList = userRepository.findAll();
		List<User> userList = new ArrayList();
		for (LdapUserModel ldapUser : ldapUserList) {
			User user = new User();
			user.setFirstName(ldapUser.getFirstName());
			user.setLastName(ldapUser.getLastName());
			user.setSsoId(ldapUser.getUid());
			user.setEmail(ldapUser.getEmail());
			userList.add(user);
		}
		return userList;
	}

	@Override
	public List<User> findLdapUserGroup() {
		/*
		 * List<LdapGroup> ldapGroups =
		 * ldapTemplate.search(query().where("objectClass").is("groupOfUniqueNames"),
		 * new GroupContextMapper());
		 */
		logger.info(LoggingMessages.getMessage("LDAP.USER.GROUP.ALL"));
		List<LdapGroup> ldapGroups = groupRepository.findAll();
		Map<String, User> userRoleMap = new HashMap();
		List<User> userList = new ArrayList();
		for (LdapGroup group : ldapGroups) {
			String roleName = group.getName();
			Set<Name> memberSet = group.getMembers();
			for (Name userName : memberSet) {
				String uid = userName.toString();
				String[] splittedString = uid.split(",");
				String uidKey = splittedString[0];
				String[] uidSplit = uidKey.split("=");
				if (userRoleMap.containsKey(uidSplit[1])) {
					User user = userRoleMap.get(uidSplit[1]);
					UserProfile up = new UserProfile();
					up.setType(roleName);
					user.getUserProfiles().add(up);
				} else {
					User user = new User();
					user.setFirstName(uidSplit[1]);
					UserProfile up = new UserProfile();
					up.setType(roleName);
					user.getUserProfiles().add(up);
					userRoleMap.put(uidSplit[1], user);
				}
			}
		}
		return getListOfUsersFromMap(userRoleMap, userList);
	}

	@Override
	public User findLdapUserBySSO(String sso) {

		logger.info(LoggingMessages.getMessage("LDAP.USER.FIND"), "SSO", sso);
		User user = null;
		/*
		 * LdapUserModel ldapUserModel = userRepository.findOne(sso); if (ldapUserModel
		 * != null) { user = new User();
		 * user.setFirstName(ldapUserModel.getFirstName());
		 * user.setLastName(ldapUserModel.getLastName());
		 * user.setSsoId(ldapUserModel.getUid());
		 * user.setEmail(ldapUserModel.getEmail()); PasswordEncoder encoder = new
		 * BCryptPasswordEncoder(); user.setPassword(encoder.encode("admin")); }
		 */
		List<LdapUserModel> ldapUserList = userRepository.findAll();
		for (LdapUserModel ldapUser : ldapUserList) {

			if (ldapUser.getUid().equalsIgnoreCase(sso)) {
				user = new User();
				user.setFirstName(ldapUser.getFirstName());
				user.setLastName(ldapUser.getLastName());
				user.setSsoId(ldapUser.getUid());
				user.setEmail(ldapUser.getEmail());
				PasswordEncoder encoder = new BCryptPasswordEncoder();
				user.setPassword(encoder.encode("admin"));
				List<LdapGroup> groups = groupRepository.findAll();
				for (LdapGroup group : groups) {
					Set<Name> memberSet = group.getMembers();
					for (Name userName : memberSet) {
						String uid = userName.toString();
						String[] splittedString = uid.split(",");
						String uidKey = splittedString[0];
						String[] uidSplit = uidKey.split("=");
						if (user.getSsoId().equals(uidSplit[1])) {
							UserProfile up = new UserProfile();
							up.setType(group.getName());
							user.getUserProfiles().add(up);
						}
					}
				}
			}
		}
		return user;
	}

	@Override
	public void uploadUsers(List<User> p) {
		logger.info(LoggingMessages.getMessage("USER.UPLOAD"), p.size());
		if (p != null && p.size() > 0) {
			for (User entity : p) {
				saveUser(entity);
			}
		}
	}

	@Override
	public void saveLdapUser(LdapUser p) {

		logger.info(LoggingMessages.getMessage("LDAP.USER.ADD"),p.getFirstName());
		userRepository.create(p);
		logger.info(LoggingMessages.getMessage("LDAP.USER.SAVED"),p.getUid());
	}

	public User findBySSOEmail(String sso, String email) {
		logger.info(LoggingMessages.getMessage("USER.FIND.EMAIL"),"sso/email", sso, email);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("ssoId", sso));
		crit.add(Restrictions.eq("email", email));
		User user = (User) crit.uniqueResult();
		// User user= mapper.retrieveUserBySSOID(sso);
		// user.setUserProfiles(mapper.getUserProfile(user.getId()));
		if (user != null) {
			Hibernate.initialize(user.getUserProfiles());
		}
		return user;

	}

}
