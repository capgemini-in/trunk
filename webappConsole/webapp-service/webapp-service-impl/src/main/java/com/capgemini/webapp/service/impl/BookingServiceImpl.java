package com.capgemini.webapp.service.impl;

import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.webapp.dao.api.BookingDao;
import com.capgemini.webapp.dao.api.UserDao;
import com.capgemini.webapp.dao.api.entity.CustomerBooking;
import com.capgemini.webapp.dao.api.entity.User;
import com.capgemini.webapp.service.api.BookingService;
import com.capgemini.webapp.service.api.model.CustomerBookingModel;
import com.capgemini.webapp.service.api.model.UserModel;

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
	
	@Autowired
	private UserDao userDao;

	public static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

	public boolean processBookingRequest(CustomerBookingModel bookingModel) {

		boolean isCreated = false;
		try {

			CustomerBooking bookingEntity = new DozerBeanMapper().map(bookingModel, CustomerBooking.class);
			isCreated = bookingDao.createBookingRequest(bookingEntity);
			
			updateUserAddress(bookingModel);

		} catch (Exception e) {

			logger.error("Error updating quotation entity:" + e.getMessage());
			isCreated = false;
		}
		return isCreated;

	}

	/**
	 * Update the user Address, when he/she selects the Book Online Option 
	 * and Entered the Country, State, City information on Payment Gateway site 
	 * and perform the Transaction..
	 * @param bookingModel
	 */
	private void updateUserAddress(CustomerBookingModel bookingModel) {
		
		UserModel userModel = bookingModel.getUser();
		User userEntity = userDao.findById(userModel.getId());
		if(userEntity != null) {
			/*userEntity.setCountry(userModel.getCountry() != null?userModel.getCountry():null);
			userEntity.setState(userModel.getState() != null?userModel.getState():null);
			userEntity.setCity(userModel.getCity() != null?userModel.getCity():null);*/
			userEntity.setAddress(userModel.getAddress() != null?userModel.getAddress():null);
			userEntity.setZipcode(userModel.getZipcode() != null?userModel.getZipcode():null);
		}
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate
	 * update explicitly. Just fetch the entity from db and update it with proper
	 * values within transaction. It will be updated in db once transaction ends.
	 */
	@Override
	public boolean updateBookingRequestWithTransactionDetails(CustomerBookingModel bookingModel) {
		boolean isUpdated = false;
		try {

			CustomerBooking bookingEntity = new DozerBeanMapper().map(bookingModel, CustomerBooking.class);
			CustomerBooking entity = bookingDao.findById(bookingEntity.getBooking_id());

			if (entity != null) {

				entity.setTransactionId(bookingModel.getTransactionId());
				entity.setStatus(bookingModel.getStatus());
				isUpdated = true;
			}

		} catch (Exception e) {

			logger.error("Error updating Booking entity:" + e.getMessage());
			isUpdated = false;
		}
		return isUpdated;
	}

}
