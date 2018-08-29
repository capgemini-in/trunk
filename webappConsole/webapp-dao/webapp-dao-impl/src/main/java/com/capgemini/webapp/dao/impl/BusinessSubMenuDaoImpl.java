package com.capgemini.webapp.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.capgemini.webapp.dao.api.BusinessSubMenuDao;
import com.capgemini.webapp.dao.api.entity.BusinessSubMenu;

@Repository("subMenuDao")
public class BusinessSubMenuDaoImpl extends AbstractDao<Integer,BusinessSubMenu> implements BusinessSubMenuDao{

	@Override
	public BusinessSubMenu getVariantsForCategory(String subMenuId) {
		Criteria crit = createEntityCriteria();
		int sbMenuId = Integer.parseUnsignedInt(subMenuId);
		crit.add(Restrictions.eq("subMenuId", sbMenuId));
		BusinessSubMenu bt = (BusinessSubMenu) crit.uniqueResult();
		if (bt != null) {
			Hibernate.initialize(bt.getCategory());
		}
		return bt;
	}

}
