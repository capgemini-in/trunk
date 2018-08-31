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
import com.capgemini.webapp.dao.api.entity.Dealer;
import com.capgemini.webapp.service.api.DealerService;
import com.capgemini.webapp.service.api.model.DealerModel;


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
	
}