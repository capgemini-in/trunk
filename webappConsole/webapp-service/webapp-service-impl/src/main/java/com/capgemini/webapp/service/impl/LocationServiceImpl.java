package com.capgemini.webapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.webapp.dao.api.CategoryDao;
import com.capgemini.webapp.dao.api.LocationDao;
import com.capgemini.webapp.dao.api.entity.BusinessMenu;
import com.capgemini.webapp.dao.api.entity.BusinessType;
import com.capgemini.webapp.dao.api.entity.Category;
import com.capgemini.webapp.dao.api.entity.Country;
import com.capgemini.webapp.dao.api.entity.State;
import com.capgemini.webapp.service.api.LocationService;
import com.capgemini.webapp.service.api.model.CategoryModel;
import com.capgemini.webapp.service.api.model.CountryModel;

@Service("locationService")
@Transactional
public class LocationServiceImpl implements LocationService {
	
	
	
	@Autowired
	private LocationDao dao;
	
	@Autowired
	private CategoryDao catDao;
	
	public List<CountryModel> getAllCountries(){
		List<CountryModel> countryList=new ArrayList<CountryModel> ();
		countryList=this.mapList(dao.getAllCountries(), CountryModel.class); 
		return countryList;
	}
	
	
	private List<CountryModel> mapList(List<Country> fromList, final Class<CountryModel> toClass) {
	
			
		return fromList.stream().map(from -> new DozerBeanMapper().map(from, toClass)).collect(Collectors.toList());
	}
	
	public List<CategoryModel> getCategoryDetail(String subMenuId, String businessTypeId){
		List<CategoryModel> categoryList=new ArrayList<CategoryModel> ();
		List< Category> catList=catDao.getCategoryforMenu(subMenuId, businessTypeId);
		
		for(Category c: catList) {
			
		        System.out.println(c.getCategoryName());
		    }
		

		// categoryList=this.mapCategoryList(catDao.getCategoryforMenu(menuId, businessTypeId), CategoryModel.class); 
		return categoryList;
	}
	
	
	private List<CategoryModel> mapCategoryList(List<Category> fromList, final Class<CategoryModel> toClass) {
				
		return fromList.stream().map(from -> new DozerBeanMapper().map(from, toClass)).collect(Collectors.toList());
	}
}
