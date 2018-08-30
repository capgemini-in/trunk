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

import com.capgemini.webapp.dao.api.BusinessSubMenuDao;
import com.capgemini.webapp.dao.api.CategoryDao;
import com.capgemini.webapp.dao.api.LocationDao;
import com.capgemini.webapp.dao.api.entity.BusinessMenu;
import com.capgemini.webapp.dao.api.entity.BusinessSubMenu;
import com.capgemini.webapp.dao.api.entity.BusinessType;
import com.capgemini.webapp.dao.api.entity.Category;
import com.capgemini.webapp.dao.api.entity.CategoryVariants;
import com.capgemini.webapp.dao.api.entity.Country;
import com.capgemini.webapp.dao.api.entity.State;
import com.capgemini.webapp.service.api.LocationService;
import com.capgemini.webapp.service.api.model.BusinessSubMenuModel;
import com.capgemini.webapp.service.api.model.BusinessTypeModel;
import com.capgemini.webapp.service.api.model.CategoryModel;
import com.capgemini.webapp.service.api.model.CategoryVariantsModel;
import com.capgemini.webapp.service.api.model.CountryModel;
import com.capgemini.webapp.service.api.model.SubMenuCategoryModel;

@Service("locationService")
@Transactional
public class LocationServiceImpl implements LocationService {
	
	
	
	@Autowired
	private LocationDao dao;
	
	@Autowired
	private CategoryDao catDao;
	
	@Autowired
	private BusinessSubMenuDao subMenuDao;
	
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
		categoryList=this.mapCategoryList(catList, CategoryModel.class); 
		return categoryList;
	}
	
	
	private List<CategoryModel> mapCategoryList(List<Category> fromList, final Class<CategoryModel> toClass) {
				
		return fromList.stream().map(from -> new DozerBeanMapper().map(from, toClass)).collect(Collectors.toList());
	}


	@Override
	public SubMenuCategoryModel getVariantsForCategory(String subMenuId) {
		SubMenuCategoryModel subMenuModel = null;
		BusinessSubMenu subMenu=subMenuDao.getVariantsForCategory(subMenuId);
		if (subMenu != null) {
			subMenuModel = new DozerBeanMapper().map(subMenu, SubMenuCategoryModel.class);
		} 
		return subMenuModel;
	}


	@Override
	public void getVariantColor(String variantId) {
		// TODO Auto-generated method stub
		
	}
	
	
}
