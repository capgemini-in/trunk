/**
 * 
 */
package com.capgemini.webapp.dao.api;

import java.util.List;

import com.capgemini.webapp.dao.api.entity.CustomerBooking;

/**
 * @author vipsatpu
 *
 */
public interface BookingDao {

	public boolean createBookingRequest(CustomerBooking customerBooking);
	
	
}
