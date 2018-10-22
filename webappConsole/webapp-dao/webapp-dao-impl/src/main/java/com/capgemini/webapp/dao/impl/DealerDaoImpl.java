package com.capgemini.webapp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.capgemini.webapp.common.utils.LoggingMessages;
import com.capgemini.webapp.dao.api.DealerDao;
import com.capgemini.webapp.dao.api.entity.Dealer;

@Repository("dealerDao")
public class DealerDaoImpl extends AbstractDao<Integer, Dealer> implements DealerDao {

	/*
	 * public List<Dealer> getDealerByStateCity(int stateId,int cityId){
	 * 
	 * 
	 * Criteria criteria = createEntityCriteriawithAlias("dealer");
	 * criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	 * criteria.add(Restrictions.eq("dealer.city", cityId));
	 * //criteria.add(Restrictions.eq("dealer.state", stateId)); List<Dealer>
	 * dealerList = (List<Dealer>) criteria.list(); return dealerList;
	 * 
	 * }
	 */

	static final Logger logger = LoggerFactory.getLogger(DealerDaoImpl.class);

	public List<Dealer> getDealerByStateCity(int stateId, int cityId) {

		logger.info(LoggingMessages.getMessage("Dealer.FIND"), stateId, cityId);
		Criteria criteria = createEntityCriteriawithAlias("dealer2");
		criteria.createAlias("dealer2.city", "city");
		criteria.setFetchMode("dealer2.city", FetchMode.LAZY);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid duplicates.
		criteria.add(Restrictions.eq("city.cityId", 1));
		List<Dealer> dealerList = (List<Dealer>) criteria.list();
		return dealerList;
	}
}
