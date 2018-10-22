package com.capgemini.webapp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.capgemini.webapp.common.utils.LoggingMessages;
import com.capgemini.webapp.dao.api.QuotationDao;
import com.capgemini.webapp.dao.api.entity.Quotation;

@Repository("quotationDao")
public class QuotationDaoImpl extends AbstractDao<Integer, Quotation> implements QuotationDao {

	public static final Logger logger = LoggerFactory.getLogger(QuotationDaoImpl.class);

	@Override
	public boolean createQuotationRequest(Quotation quotation) {

		logger.info(LoggingMessages.getMessage("Quotation.CREATE"), quotation.getUser().getSsoId(), quotation.getVariant().getVariantName());
		boolean status = false;
		try {
			getSession().save(quotation);
			status = true;
			logger.info(LoggingMessages.getMessage("Quotation.SAVED"), quotation.getUser().getSsoId(),
					quotation.getQuotation_id());
		} catch (Exception e) {
			logger.info(LoggingMessages.getMessage("Quotation.SAVE.ERROR"), quotation.getUser().getSsoId(),
					e.getMessage());
			return false;
		}
		return status;

	}

	public List<Quotation> getQuotationForDealer(int dealerId) {
		List<Quotation> quotationList = null;
		logger.info(LoggingMessages.getMessage("Quotation.FIND"), "FOR DEALER", dealerId);
		try {

			Criteria criteria = createEntityCriteria();
			// criteria.createAlias("quotation.dealer", "dealer");
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid duplicates.
			criteria.add(Restrictions.eq("dealer.id", dealerId));
			quotationList = (List<Quotation>) criteria.list();

		} catch (Exception e) {
			logger.error(LoggingMessages.getMessage("Quotation.FIND.ERROR"), dealerId, e.getMessage());
			// logger.debug("Exception retrieving user"+ e.getMessage());

		}
		return quotationList;
	}

	public List<Quotation> getQuotationForUser(int userId) {

		List<Quotation> quotationList = null;
		logger.info(LoggingMessages.getMessage("Quotation.FIND"), "BY CUSTOMER", userId);
		try {

			Criteria criteria = createEntityCriteria();
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid duplicates.
			criteria.add(Restrictions.eq("user.id", userId));
			quotationList = (List<Quotation>) criteria.list();

		} catch (Exception e) {
			logger.error(LoggingMessages.getMessage("Quotation.FIND.ERROR"), userId, e.getMessage());
			// logger.debug("Exception retrieving user"+ e.getMessage());

		}
		return quotationList;
	}

	@Override
	public boolean saveQuotation(Quotation quot) {
		logger.info(LoggingMessages.getMessage("Quotation.CREATE"), quot.getUser().getSsoId(),quot.getVariant().getVariantName());
		boolean status = false;
		try {

			getSession().save(quot);
			status = true;
			logger.info(LoggingMessages.getMessage("Quotation.SAVED"), quot.getUser().getSsoId(),
					quot.getQuotation_id());
		} catch (Exception e) {
			logger.info(LoggingMessages.getMessage("Quotation.SAVE.ERROR"), quot.getUser().getSsoId(), e.getMessage());
			status = false;

		}
		return status;

	}

	@Override
	public Quotation findById(int quotationId) {
		logger.info(LoggingMessages.getMessage("Quotation.FIND"), "Quotation Id", quotationId);
		Quotation quotation = getByKey(quotationId);
		return quotation;
	}

}