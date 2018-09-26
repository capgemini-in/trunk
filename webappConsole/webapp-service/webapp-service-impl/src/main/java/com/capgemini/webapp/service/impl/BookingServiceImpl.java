package com.capgemini.webapp.service.impl;

import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.webapp.dao.api.BookingDao;
import com.capgemini.webapp.dao.api.entity.CustomerBooking;
import com.capgemini.webapp.service.api.BookingService;
import com.capgemini.webapp.service.api.model.CustomerBookingModel;

/**
 * Booking Service implementation. Send SMS using MSG91 SMS provider POST APIs
 * 
 * @author pallapat
 *
 */

@Service("bookingService")
@Transactional
public class BookingServiceImpl implements BookingService {


@Autowired
private BookingDao bookingDao;
		
public static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

public boolean processBookingRequest(CustomerBookingModel bookingModel) {
	
	
		boolean isCreated=false;
		try {			
			
			CustomerBooking bookingEntity=new DozerBeanMapper().map(bookingModel, CustomerBooking.class);
			isCreated=bookingDao.createBookingRequest(bookingEntity);
			
		}catch(Exception e) {
			
			logger.error("Error updating quotation entity:"+e.getMessage());
			isCreated=false;
		}
		return isCreated;
	
}
	
	
	
	
}
