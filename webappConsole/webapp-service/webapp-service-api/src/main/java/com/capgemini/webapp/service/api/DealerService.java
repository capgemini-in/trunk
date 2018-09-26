package com.capgemini.webapp.service.api;

import java.util.List;

import com.capgemini.webapp.service.api.model.CustomerBookingModel;
import com.capgemini.webapp.service.api.model.DealerModel;
import com.capgemini.webapp.service.api.model.QuotationModel;

public interface DealerService {
	
	public List<DealerModel> getAllDealerforStateCity(String stateId, String cityId);
	
	public boolean processQuotationRequest(QuotationModel quotationModel) ;
	
	public List<QuotationModel> getQuotationRequest(String dealerId);

	boolean updateQuotationRequest(QuotationModel quotationModel);
	
	public List<QuotationModel> getQuotationforUser(String userId);

	
}
