package com.capgemini.webapp.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.capgemini.webapp.dao.api.BusinessTypeDao;
import com.capgemini.webapp.dao.api.entity.BusinessType;

@Repository("menuDao")
public class BusinessTypeDaoImpl extends AbstractDao<Integer, BusinessType> implements BusinessTypeDao{

	@Override
	public BusinessType getAllBusinessMenus(String busType) {
		
		
		/*Criteria criteria = createEntityCriteria().addOrder(Order.asc("bus_type_id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid duplicates.
		List<BusinessType> menuList = (List<BusinessType>) criteria.list();
		return menuList;*/
		
		//logger.info("busType : {}", busType);
		Criteria crit = createEntityCriteria();
		int busTypeId = Integer.parseUnsignedInt(busType);
		crit.add(Restrictions.eq("busTypeId", busTypeId));
		BusinessType bt = (BusinessType) crit.uniqueResult();
		// User user= mapper.retrieveUserBySSOID(sso);
		// user.setUserProfiles(mapper.getUserProfile(user.getId()));
		if (bt != null) {
			Hibernate.initialize(bt.getMenus());
		}
		return bt;
	}

}
