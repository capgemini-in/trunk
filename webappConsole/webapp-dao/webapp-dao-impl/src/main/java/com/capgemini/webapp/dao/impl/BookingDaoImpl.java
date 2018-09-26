package com.capgemini.webapp.dao.impl;

import org.springframework.stereotype.Repository;

import com.capgemini.webapp.dao.api.BookingDao;
import com.capgemini.webapp.dao.api.entity.CustomerBooking;



@Repository("bookingDao")
public class BookingDaoImpl extends AbstractDao<Integer,CustomerBooking> implements BookingDao{

	@Override
	public boolean createBookingRequest(CustomerBooking customerBooking) {
		
		boolean status=false;
		try {
				getSession().save(customerBooking);
				status=true;
				}catch(Exception e) {
					return false;
					
				}
			return status;
	}
	
	

}
