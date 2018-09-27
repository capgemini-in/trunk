/**
 * 
 */
package com.capgemini.webapp.dao.api;

import java.util.List;

import com.capgemini.webapp.dao.api.entity.Quotation;

/**
 * @author vipsatpu
 *
 */
public interface QuotationDao {

	public boolean createQuotationRequest(Quotation quotation);
	public List<Quotation> getQuotationForDealer(int dealerId);
	public List<Quotation> getQuotationForUser(int userId);
	public Quotation findById(int quotationId);
	boolean saveQuotation(Quotation quot);
	
}
