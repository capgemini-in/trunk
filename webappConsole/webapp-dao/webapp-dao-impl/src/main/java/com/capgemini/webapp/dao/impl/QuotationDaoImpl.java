package com.capgemini.webapp.dao.impl;

import org.springframework.stereotype.Repository;

import com.capgemini.webapp.dao.api.QuotationDao;
import com.capgemini.webapp.dao.api.entity.Quotation;


@Repository("quotationDao")
public class QuotationDaoImpl extends AbstractDao<Integer,Quotation> implements QuotationDao{ 

@Override
public boolean createQuotationRequest(Quotation quotation) {
	
	
		boolean status=false;
			try {
			getSession().save(quotation);
			status=true;
			}catch(Exception e) {
				return false;
				
			}
		return status;
	
	}
}