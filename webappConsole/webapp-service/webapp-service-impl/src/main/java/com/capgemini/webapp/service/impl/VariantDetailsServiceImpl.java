package com.capgemini.webapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.webapp.dao.api.VariantDetailsDao;
import com.capgemini.webapp.dao.api.entity.VariantDetails;
import com.capgemini.webapp.service.api.VariantDetailsService;
import com.capgemini.webapp.service.api.model.VariantDetailsModel;

@Service("variantDetailsService")
@Transactional
public class VariantDetailsServiceImpl implements VariantDetailsService {

	
	@Autowired
	VariantDetailsDao variantDetailsDao;

	public static final Logger logger = LoggerFactory.getLogger(DealerServiceImpl.class);
	
	@Override
	public List<VariantDetailsModel> getVariantDetails(String variantId, String fuelType) {
		
		logger.debug("Inside Mehtod of VariantDetailsServiceImpl.getVariantDetails () ");
		List<VariantDetailsModel> variantDetailModel = new ArrayList<>();
		List<VariantDetails> variantDetails = variantDetailsDao.getVariantDetails(variantId,fuelType);		
		if (variantDetails != null && variantDetails.size() >0) {
			variantDetailModel = mapList(variantDetails,VariantDetailsModel.class);
		} 		
		return variantDetailModel;
	}
	
	private List<VariantDetailsModel> mapList(List<VariantDetails> allBusinessMenus, Class<VariantDetailsModel> toClass) {
		// TODO Auto-generated method stub
		return allBusinessMenus.stream().map(from -> new DozerBeanMapper().map(from, toClass)).collect(Collectors.toList());
	}
}
