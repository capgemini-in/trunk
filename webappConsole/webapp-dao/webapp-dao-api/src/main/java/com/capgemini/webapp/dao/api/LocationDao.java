package com.capgemini.webapp.dao.api;

import java.util.List;

import com.capgemini.webapp.dao.api.entity.Country;


public interface LocationDao {
	
	public List<Country> getAllCountries();

}
 