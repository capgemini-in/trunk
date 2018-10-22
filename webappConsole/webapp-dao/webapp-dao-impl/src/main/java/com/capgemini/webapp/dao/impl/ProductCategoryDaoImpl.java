package com.capgemini.webapp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.capgemini.webapp.common.utils.LoggingMessages;
import com.capgemini.webapp.dao.api.ProductCategoryDao;
import com.capgemini.webapp.dao.api.entity.ProductCategory;


/**
 * DAO handles product category related database operations
 * @author pallapat
 *
 */
@Repository("productCategoryDao")
public class ProductCategoryDaoImpl extends AbstractDao<Integer,ProductCategory>implements ProductCategoryDao{

	static final Logger logger = LoggerFactory.getLogger(ProductCategoryDaoImpl.class);
	@Override
	public List<ProductCategory> findAllProductCategory() {
		logger.info(LoggingMessages.getMessage("ProductCategory.ALL"));
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid duplicates.
		List<ProductCategory> prodCatList = (List<ProductCategory>) criteria.list();
		return prodCatList;
	}

}
