package com.capgemini.webapp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.capgemini.webapp.common.utils.LoggingMessages;
import com.capgemini.webapp.dao.api.LocationDao;
import com.capgemini.webapp.dao.api.entity.Country;

@Repository("locationDao")
public class LocationDaoImpl extends AbstractDao<Integer, Country> implements LocationDao {

	static final Logger logger = LoggerFactory.getLogger(LocationDaoImpl.class);
	public List<Country> getAllCountries() {
		logger.info(LoggingMessages.getMessage("Countries.ALL"));
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("countryId"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid duplicates.
		List<Country> countryList = (List<Country>) criteria.list();
		return countryList;
	}

}
