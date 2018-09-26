package com.capgemini.webapp.service.api;

import java.util.List;

import com.capgemini.webapp.service.api.model.CustomerBookingModel;
import com.capgemini.webapp.service.api.model.DealerModel;
import com.capgemini.webapp.service.api.model.QuotationModel;

public interface BookingService {
	
	public boolean processBookingRequest(CustomerBookingModel bookingModel);
}
