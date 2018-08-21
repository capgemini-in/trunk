package com.capgemini.webapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.capgemini.webapp.common.constants.IApplicationConstants;
import com.capgemini.webapp.dao.api.MenuDao;
import com.capgemini.webapp.dao.api.entity.BusinessMenu;
import com.capgemini.webapp.service.api.BusinessMenuService;
import com.capgemini.webapp.service.api.model.BusinessMenuModel;
import com.capgemini.webapp.service.api.model.CountryModel;


@Service("menuService")
@PropertySource("classpath:/config/ldap/ldap_details.properties")
public class BusinessMenuServiceImpl implements BusinessMenuService{

	
	public static final Logger logger = LoggerFactory.getLogger(BusinessMenuServiceImpl.class);

	@Autowired
	private Environment env;

	@Autowired
	private MenuDao dao;

	@Override
	public List<BusinessMenuModel> getAllBusinessMenus() {
		
		List<BusinessMenuModel> menuModelList = null;
		String businessName = env.getRequiredProperty(IApplicationConstants.BUSINESS_NAME);
		String businessType = env.getRequiredProperty(IApplicationConstants.BUSINESS_TYPE);
		menuModelList=this.mapList(dao.getAllBusinessMenus(), BusinessMenuModel.class); 
		return menuModelList;
	}

	/**
	 * 
	 * @param allBusinessMenus
	 * @param class1
	 * @return
	 */
	private List<BusinessMenuModel> mapList(List<BusinessMenu> allBusinessMenus, Class<BusinessMenuModel> toClass) {
		// TODO Auto-generated method stub
		return allBusinessMenus.stream().map(from -> new DozerBeanMapper().map(from, toClass)).collect(Collectors.toList());
	}

}
