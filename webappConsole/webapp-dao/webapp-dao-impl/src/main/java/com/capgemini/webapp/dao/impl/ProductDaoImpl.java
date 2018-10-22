package com.capgemini.webapp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.capgemini.webapp.common.utils.LoggingMessages;
import com.capgemini.webapp.dao.api.ProductDao;
import com.capgemini.webapp.dao.api.entity.Product;

/**
 *  DAO handles product related database operations
 * @author pallapat
 *
 */

@Repository("productDao")
public class ProductDaoImpl extends AbstractDao<Integer, Product>implements ProductDao {

	static final Logger logger = LoggerFactory.getLogger(ProductDaoImpl.class);
	
	@Override
	public List<Product> findAllProducts() {
		logger.info(LoggingMessages.getMessage("Product.ALL"));
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid duplicates.
		List<Product> prodList = (List<Product>) criteria.list();
		return prodList;
	}
	
	public Product findByProdID(String prodId) {
		logger.info(LoggingMessages.getMessage("Product.FIND"), prodId);
		//logger.info("ProdID : {}", prodId);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("prodId", prodId));
		Product product = (Product) crit.uniqueResult();
		if (product != null) {
			Hibernate.initialize(product.getProdCategory());
		}
		return product;
	}

	@Override
	public boolean saveProduct(Product prod) {
		logger.info(LoggingMessages.getMessage("Product.NEW"), prod.getName());
		boolean status=false;
			try {
			getSession().save(prod);
			logger.info(LoggingMessages.getMessage("Product.SAVED"), prod.getProdCategory(),prod.getName(),prod.getId());
			status=true;
			}catch(Exception e) {
				logger.info(LoggingMessages.getMessage("Product.SAVE.ERROR"), prod.getName(),e.getMessage());
				return false;
				
			}
		return status;
	
	}
	
	public void deleteByProdId(String prodId) {
		logger.info(LoggingMessages.getMessage("Product.DELETE"), prodId);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("prodId", prodId));
		Product prod = (Product) crit.uniqueResult();
		delete(prod);
		logger.info(LoggingMessages.getMessage("Product.DELETED"), prodId);
	}


}
