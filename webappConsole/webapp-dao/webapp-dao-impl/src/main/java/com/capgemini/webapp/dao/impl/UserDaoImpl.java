package com.capgemini.webapp.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capgemini.webapp.dao.api.UserDao;
import com.capgemini.webapp.dao.api.entity.User;
import com.capgemini.webapp.dao.impl.config.mybatis.mapper.usermapper.UserMapper;



@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

	static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	@Autowired
	UserMapper mapper;
	
	public User findById(int id) {
		User user = getByKey(id);
		if(user!=null){
			Hibernate.initialize(user.getUserProfiles());
		}
		return user;
	}

	public User findBySSO(String sso) {
		logger.info("SSO : {}", sso);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("ssoId", sso));
		User user = (User)crit.uniqueResult();
		//User user= mapper.retrieveUserBySSOID(sso);
		//user.setUserProfiles(mapper.getUserProfile(user.getId()));
		if(user!=null){
			Hibernate.initialize(user.getUserProfiles());
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("firstName"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		List<User> users = (List<User>) criteria.list();
		
		// No need to fetch userProfiles since we are not showing them on list page. Let them lazy load. 
		// Uncomment below lines for eagerly fetching of userProfiles if you want.
		/*
		for(User user : users){
			Hibernate.initialize(user.getUserProfiles());
		}*/
		return users;
	}

	public void save(User user) {
		saveUser(user);
	}
    
	public long saveUser(User user) {
		//User existinguser = findUserBySSOID(user.getSsoId());
			getSession().save(user);
		return 1;
	}
	public User findUserBySSOID(String ssoId) {
		//Query query = getSession().getNamedNativeQuery(User.QUERY_FIND_USER_BY_SSOID);
		EntityManager em = Persistence.createEntityManagerFactory("JPA").createEntityManager();
        TypedQuery <User> namedQuery = em.createNamedQuery(User.QUERY_FIND_USER_BY_SSOID, User.class);
        namedQuery.setParameter("ssoId",ssoId.toLowerCase());
		User user = (User) namedQuery.getSingleResult();
		System.out.println(user);
		return user;
	}
	
	public void deleteBySSO(String sso) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("ssoId", sso));
		User user = (User)crit.uniqueResult();
		delete(user);
	}

}
