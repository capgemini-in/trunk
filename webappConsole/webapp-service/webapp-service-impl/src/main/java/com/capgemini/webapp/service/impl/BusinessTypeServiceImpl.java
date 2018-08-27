package com.capgemini.webapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.capgemini.webapp.common.constants.IApplicationConstants;
import com.capgemini.webapp.dao.api.BusinessTypeDao;
import com.capgemini.webapp.dao.api.entity.BusinessType;
import com.capgemini.webapp.service.api.BusinessTypeService;
import com.capgemini.webapp.service.api.model.BusinessTypeModel;


@Service("menuService")
@Transactional
@PropertySource("classpath:/config/ldap/ldap_details.properties")
public class BusinessTypeServiceImpl implements BusinessTypeService{

	
	public static final Logger logger = LoggerFactory.getLogger(BusinessTypeServiceImpl.class);

	@Autowired
	private Environment env;

	@Autowired
	private BusinessTypeDao dao;

	@Override
	public BusinessTypeModel getAllBusinessMenus() {
		
		BusinessTypeModel typeModel = null;
		
		String businessName = env.getRequiredProperty(IApplicationConstants.BUSINESS_NAME);
		String businessType = env.getRequiredProperty(IApplicationConstants.BUSINESS_TYPE);
		
		BusinessType btype = null;
		btype = dao.getAllBusinessMenus(businessType);
		if (btype != null) {
			typeModel = new DozerBeanMapper().map(btype, BusinessTypeModel.class);
		}
		return typeModel;
		//menuModelList=this.mapList(dao.getAllBusinessMenus(businessType), BusinessTypeModel.class); 
		//return menuModelList;
	}

	/**
	 * 
	 * @param allBusinessMenus
	 * @param class1
	 * @return
	 */
	private List<BusinessTypeModel> mapList(List<BusinessType> allBusinessMenus, Class<BusinessTypeModel> toClass) {
		// TODO Auto-generated method stub
		return allBusinessMenus.stream().map(from -> new DozerBeanMapper().map(from, toClass)).collect(Collectors.toList());
	}

}
