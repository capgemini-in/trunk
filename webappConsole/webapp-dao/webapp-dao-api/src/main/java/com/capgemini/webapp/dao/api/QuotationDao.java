/**
 * 
 */
package com.capgemini.webapp.dao.api;

import java.util.List;

import com.capgemini.webapp.dao.api.entity.Dealer;
import com.capgemini.webapp.dao.api.entity.Quotation;

/**
 * @author vipsatpu
 *
 */
public interface QuotationDao {

	public boolean createQuotationRequest(Quotation quotation);
	
}
