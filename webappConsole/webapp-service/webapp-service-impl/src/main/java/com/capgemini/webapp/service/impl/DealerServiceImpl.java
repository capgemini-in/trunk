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
 * 
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
	

	//@Autowired
	//DealerDao2 dealerDao2;


	public static final Logger logger = LoggerFactory.getLogger(DealerServiceImpl.class);

	@Override
	public List<DealerModel> getAllDealerforStateCity(String stateId, String cityId) {

		logger.debug("DealerServiceImpl:getAllDealerforStateCity:Initiated Execution");

		List<DealerModel> dealerList = null;
		try {
		//	dealerDao2.getDealerByStateCity(Integer.parseUnsignedInt(stateId), Integer.parseUnsignedInt(cityId));
			dealerList = this.mapList(
					dealerDao.getDealerByStateCity(Integer.parseUnsignedInt(stateId), Integer.parseUnsignedInt(cityId)),
					DealerModel.class);

		} catch (Exception e) {

			logger.error("Error retrieving dealers:" + e.getMessage());
			return null;
		}
		logger.debug("DealerServiceImpl:getAllDealerforStateCity:Completed Execution");

		return dealerList;

	}

	/* this is Mapper for List */
	private List<DealerModel> mapList(List<Dealer> fromList, final Class<DealerModel> toClass) {
		return fromList.stream().map(from -> new DozerBeanMapper().map(from, toClass)).collect(Collectors.toList());
	}

	@Override
	public boolean processQuotationRequest(QuotationModel quotationModel) {
		logger.debug("DealerServiceImpl:processQuotationRequest:Initiated Execution");
		boolean isCeated = false;
		try {

			Quotation quotationEntity = new DozerBeanMapper().map(quotationModel, Quotation.class);
			isCeated = quotationDao.createQuotationRequest(quotationEntity);

		} catch (Exception e) {

			logger.error("Error updating quotation entity:" + e.getMessage());
			isCeated = false;
		}
		logger.debug("DealerServiceImpl:processQuotationRequest:Completed Execution");
		return isCeated;
	}

	@Override
	public List<QuotationModel> getQuotationRequest(String dealerId) {

		logger.debug("DealerServiceImpl:getQuotationRequest:Initiated Execution");
		List<QuotationModel> quotationList = null;
		try {

			quotationList = this.mapDealerList(quotationDao.getQuotationForDealer(Integer.parseUnsignedInt(dealerId)),
					QuotationModel.class);

		} catch (Exception e) {

			logger.error("Error retrieving dealers:" + e.getMessage());
			return null;
		}
		logger.debug("DealerServiceImpl:getQuotationRequest:Completed Execution");

		return quotationList;

	}

	@Override
	public List<QuotationModel> getQuotationforUser(String userId) {

		logger.debug("DealerServiceImpl:getQuotationRequest+Retrieving quotation request");
		List<QuotationModel> quotationList = null;
		try {

			quotationList = this.mapDealerList(quotationDao.getQuotationForUser(Integer.parseUnsignedInt(userId)),
					QuotationModel.class);

		} catch (Exception e) {

			logger.error("Error retrieving dealers:" + e.getMessage());
			return null;
		}
		logger.debug("getAllProduct+Completed Retrieving products list");

		return quotationList;

	}

	@Override
	public boolean updateQuotationRequest(QuotationModel quotationModel) {

		logger.debug("DealerServiceImpl:updateQuotationRequest+Initiated Execution");
		boolean isUpdate = false;
		try {

			// Quotation quotationEntity=new DozerBeanMapper().map(quotationModel,
			// Quotation.class);

			Quotation quotationEntity = quotationDao.findById(quotationModel.getQuotation_id());
			quotationEntity.setDiscountedPrice(quotationModel.getDiscountedPrice());
			quotationEntity.setFilePath(quotationModel.getFilePath());
			isUpdate = true;
			// quotationDao.saveQuotation(quotationEntity);

		} catch (Exception e) {

			logger.error("Error retrieving dealers:" + e.getMessage());
			return isUpdate;
		}
		logger.debug("DealerServiceImpl:updateQuotationRequest:Completed Execution");

		return isUpdate;

	}

	/* this is Mapper for List */
	private List<QuotationModel> mapDealerList(List<Quotation> fromList, final Class<QuotationModel> toClass) {
		if (fromList != null)
			return fromList.stream().map(from -> new DozerBeanMapper().map(from, toClass)).collect(Collectors.toList());
		else
			return null;
	}

}
