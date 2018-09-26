/**
 * 
 */
package com.capgemini.webapp.dao.api;

import com.capgemini.webapp.dao.api.entity.CustomerBooking;

/**
 * @author vipsatpu
 *
 */
public interface BookingDao {

	public boolean createBookingRequest(CustomerBooking customerBooking);

	public CustomerBooking findById(int booking_id);	
	
}
