package com.capgemini.webapp.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.capgemini.webapp.common.utils.LoggingMessages;
import com.capgemini.webapp.dao.api.BookingDao;
import com.capgemini.webapp.dao.api.entity.CustomerBooking;

@Repository("bookingDao")
public class BookingDaoImpl extends AbstractDao<Integer, CustomerBooking> implements BookingDao {

	static final Logger logger = LoggerFactory.getLogger(BookingDaoImpl.class);
	
	@Override
	public boolean createBookingRequest(CustomerBooking customerBooking) {

		logger.info(LoggingMessages.getMessage("Booking.INITIATED"), customerBooking.getUser().getFirstName(), customerBooking.getUser().getLastName() );
		boolean status = false;
		try {
			getSession().save(customerBooking);
			logger.info(LoggingMessages.getMessage("Booking.CREATED"), customerBooking.getUser().getFirstName(), customerBooking.getUser().getLastName(), customerBooking.getBooking_id());
			status = true;
		} catch (Exception e) {
			logger.error(LoggingMessages.getMessage("Booking.FAILED"),customerBooking.getUser().getFirstName(), customerBooking.getUser().getLastName(),e.getMessage());
			return false;
		}
		return status;
	}

	@Override
	public CustomerBooking findById(int booking_id) {
		logger.info(LoggingMessages.getMessage("Booking.SEARCH"), booking_id);
		CustomerBooking booking = getByKey(booking_id);	
		return booking;
	}

}
