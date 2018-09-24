package com.capgemini.webapp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.capgemini.webapp.dao.api.QuotationDao;
import com.capgemini.webapp.dao.api.entity.Quotation;


@Repository("quotationDao")
public class QuotationDaoImpl extends AbstractDao<Integer,Quotation> implements QuotationDao{ 

	
public static final Logger logger = LoggerFactory.getLogger(QuotationDaoImpl.class);
	
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
public List<Quotation> getQuotationForDealer(int dealerId){
	List<Quotation> quotationList =null;
	
	try {
		
	Criteria criteria = createEntityCriteria();
	//criteria.createAlias("quotation.dealer", "dealer");
	criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid duplicates.
	criteria.add(Restrictions.eq("dealer.id", dealerId));
	quotationList = (List<Quotation>) criteria.list();
	
   }catch(Exception e) {
		
	   logger.debug("Exception retrieving user"+ e.getMessage());
			
	}
	return quotationList;
}

@Override
public boolean saveQuotation(Quotation quot) {
	
	boolean status=false;
	try {
		
		getSession().save(quot);
		status=true;
	
	}catch(Exception e) {
		
		status=false;
			
	}
	return status;

}

}