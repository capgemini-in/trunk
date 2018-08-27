package com.capgemini.webapp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.capgemini.webapp.dao.api.CategoryDao;
import com.capgemini.webapp.dao.api.entity.Category;

/**
 * DAO handles Category related database operations
 * @author pallapat
 *
 */
@Repository("categoryDao")
public class CategoryDaoImpl extends AbstractDao<Integer,Category> implements CategoryDao {

	@Override
	public List<Category> getCategoryforMenu(String subMenuId,String businessType) {
		
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("submenuId", Integer.getInteger(subMenuId)));
		int busTypeId = Integer.parseUnsignedInt(subMenuId);
		//criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid duplicates.
		List<Category> catList = (List<Category>) criteria.list();		
		return catList;
	}
		


}
