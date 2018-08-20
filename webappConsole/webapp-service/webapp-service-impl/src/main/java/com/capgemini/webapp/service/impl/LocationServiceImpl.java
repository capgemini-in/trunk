package com.capgemini.webapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.webapp.dao.api.LocationDao;
import com.capgemini.webapp.dao.api.entity.Country;
import com.capgemini.webapp.dao.api.entity.State;
import com.capgemini.webapp.service.api.LocationService;
import com.capgemini.webapp.service.api.model.CountryModel;

@Service("locationService")
@Transactional
public class LocationServiceImpl implements LocationService {
	
	
	@Autowired
	private LocationDao dao;
	
	public List<CountryModel> getAllCountries(){
		List<CountryModel> countryList=new ArrayList<CountryModel> ();
		countryList=this.mapList(dao.getAllCountries(), CountryModel.class); 
		return countryList;
	}
	
	
	private List<CountryModel> mapList(List<Country> fromList, final Class<CountryModel> toClass) {
		//List<CountryModel> countryList=new ArrayLlist(())
		for(Country c:fromList) {
			System.out.println("Country "+ c.getCountryName()+ ":"+ c.getCountryId());
			
			Set<State> st=c.getState();
			System.out.println("State size"+ st.size());
			for(State state:st) {
				System.out.println("State:"+ state.getStateName()+":"+ state.getCoun());
			}
		}
		
		return fromList.stream().map(from -> new DozerBeanMapper().map(from, toClass)).collect(Collectors.toList());
	} 
}
