package com.capgemini.webapp.service.api;

import com.capgemini.webapp.service.api.model.CustomerBookingModel;

public interface BookingService {
	
	public boolean processBookingRequest(CustomerBookingModel bookingModel);
	
	public boolean updateBookingRequestWithTransactionDetails(CustomerBookingModel bookingModel);
}
