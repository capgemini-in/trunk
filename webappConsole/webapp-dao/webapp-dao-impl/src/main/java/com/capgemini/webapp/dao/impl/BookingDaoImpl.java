package com.capgemini.webapp.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.capgemini.webapp.dao.api.BookingDao;
import com.capgemini.webapp.dao.api.entity.CustomerBooking;

@Repository("bookingDao")
public class BookingDaoImpl extends AbstractDao<Integer, CustomerBooking> implements BookingDao {

	static final Logger logger = LoggerFactory.getLogger(BookingDaoImpl.class);
	
	@Override
	public boolean createBookingRequest(CustomerBooking customerBooking) {

		boolean status = false;
		try {
			getSession().save(customerBooking);
			status = true;
		} catch (Exception e) {
			return false;

		}
		return status;
	}

	@Override
	public CustomerBooking findById(int booking_id) {
		logger.info("booking_id : {}", booking_id);
		CustomerBooking booking = getByKey(booking_id);	
		return booking;
	}

}
