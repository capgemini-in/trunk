package com.capgemini.webapp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.capgemini.webapp.dao.api.LocationDao;
import com.capgemini.webapp.dao.api.entity.Country;
import com.capgemini.webapp.dao.api.entity.Product;


@Repository("locationDao")
public class LocationDaoImpl extends AbstractDao<Integer, Country> implements LocationDao{
	
	
	public List<Country> getAllCountries(){

			Criteria criteria = createEntityCriteria().addOrder(Order.asc("countryId"));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid duplicates.
			List<Country> countryList = (List<Country>) criteria.list();
			return countryList;
		}
		
		
	}

