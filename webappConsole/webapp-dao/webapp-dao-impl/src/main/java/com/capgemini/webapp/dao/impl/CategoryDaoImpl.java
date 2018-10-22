package com.capgemini.webapp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.capgemini.webapp.common.utils.LoggingMessages;
import com.capgemini.webapp.dao.api.CategoryDao;
import com.capgemini.webapp.dao.api.entity.Category;

/**
 * DAO handles Category related database operations
 * @author pallapat
 *
 */
@Repository("categoryDao")
public class CategoryDaoImpl extends AbstractDao<Integer,Category> implements CategoryDao {

	static final Logger logger = LoggerFactory.getLogger(CategoryDaoImpl.class);
	@Override
	public List<Category> getCategoryforMenu(String subMenuId,String businessType) {
		//List<Category> catList =getSession().createSQLQuery("Select category.category_name from Category where category.sub_menu_id=:SMID").setParameter("SMID", 1).list();
		logger.info(LoggingMessages.getMessage("Menu.CATEGORIES"), subMenuId);
		Criteria criteria = createEntityCriteria();
		int busTypeId = Integer.parseUnsignedInt(subMenuId);
		criteria.add(Restrictions.eq("subMenuId", busTypeId));
		//criteria.add(Restrictions.eq("submenuId", busTypeId));
		//criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid duplicates.
		List<Category> catList = (List<Category>) criteria.list();		
		return catList;
	}
		


}
