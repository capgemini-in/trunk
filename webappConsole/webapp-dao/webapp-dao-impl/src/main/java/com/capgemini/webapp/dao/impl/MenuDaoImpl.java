package com.capgemini.webapp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.capgemini.webapp.dao.api.MenuDao;
import com.capgemini.webapp.dao.api.entity.BusinessMenu;

@Repository("menuDao")
public class MenuDaoImpl extends AbstractDao<Integer, BusinessMenu> implements MenuDao{

	@Override
	public List<BusinessMenu> getAllBusinessMenus() {
		
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("businessId"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid duplicates.
		List<BusinessMenu> menuList = (List<BusinessMenu>) criteria.list();
		return menuList;
	}

}
