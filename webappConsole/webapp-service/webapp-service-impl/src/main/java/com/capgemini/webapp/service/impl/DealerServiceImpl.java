package com.capgemini.webapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.webapp.dao.api.DealerDao;
import com.capgemini.webapp.dao.api.QuotationDao;
import com.capgemini.webapp.dao.api.entity.Dealer;
import com.capgemini.webapp.dao.api.entity.Quotation;
import com.capgemini.webapp.service.api.DealerService;
import com.capgemini.webapp.service.api.model.DealerModel;
import com.capgemini.webapp.service.api.model.QuotationModel;


/**
 * Service handling dealer related operations
 * @author pallapat
 *
 */
@Service("dealerService")
@Transactional
public class DealerServiceImpl implements DealerService {
	
	
	@Autowired
	DealerDao dealerDao;
	
	@Autowired 
	QuotationDao quotationDao;

	public static final Logger logger = LoggerFactory.getLogger(DealerServiceImpl.class);
	
	@Override
	public List<DealerModel> getAllDealerforStateCity(String stateId, String cityId) {

		
			
			logger.debug("getAllDealerforStateCity+Retrieving dealer");
			
			List<DealerModel> dealerList = null;
			try {
				
				dealerList= this.mapList(dealerDao.getDealerByStateCity(Integer.parseUnsignedInt(stateId), Integer.parseUnsignedInt(cityId)), DealerModel.class);
				
			}catch(Exception e) {
				
				logger.error("Error retrieving dealers:"+e.getMessage());
				return null;
			}
			logger.debug("getAllProduct+Completed Retrieving products list");

			return dealerList;
		
	
	}
	
	/*this is Mapper for List*/
    private List<DealerModel> mapList(List<Dealer> fromList, final Class<DealerModel> toClass) {
	    return fromList
	            .stream()
	            .map(from -> new DozerBeanMapper().map(from, toClass))
	            .collect(Collectors.toList());
	}

	@Override
	public boolean processQuotationRequest(QuotationModel quotationModel) {
		boolean isCeated=false;
		try {
			
			
			Quotation quotationEntity=new DozerBeanMapper().map(quotationModel, Quotation.class);
			isCeated=quotationDao.createQuotationRequest(quotationEntity);
			
		}catch(Exception e) {
			
			logger.error("Error updating quotation entity:"+e.getMessage());
			isCeated=false;
		}
		return isCeated;
	}
	
	
	@Override
	public List<QuotationModel> getQuotationRequest(String dealerId) {

		
			
			logger.debug("getQuotationRequest+Retrieving quotation request");			
			List<QuotationModel> quotationList = null;
			try {
				
				quotationList= this.mapDealerList(quotationDao.getQuotationForDealer(Integer.parseUnsignedInt(dealerId)), QuotationModel.class);
				
			}catch(Exception e) {
				
				logger.error("Error retrieving dealers:"+e.getMessage());
				return null;
			}
			logger.debug("getAllProduct+Completed Retrieving products list");

			return quotationList;
		
	
	}
	
	@Override
	public boolean updateQuotationRequest(QuotationModel quotationModel) {
		
			
			logger.debug("Updating QuotationRequest");			
		    boolean isUpdate=false;
			try {
				
				Quotation quotationEntity=new DozerBeanMapper().map(quotationModel, Quotation.class);
				quotationDao.saveQuotation(quotationEntity);
				
			}catch(Exception e) {
				
				logger.error("Error retrieving dealers:"+e.getMessage());
				return isUpdate;
			}
			logger.debug("getAllProduct+Completed Retrieving products list");

			return isUpdate;
		
	
	}
	
	/*this is Mapper for List*/
    private List<QuotationModel> mapDealerList(List<Quotation> fromList, final Class<QuotationModel> toClass) {
    	if(fromList!=null)
		    return fromList
		            .stream()
		            .map(from -> new DozerBeanMapper().map(from, toClass))
		            .collect(Collectors.toList());
    	else
    		return null;
	}

	
}
