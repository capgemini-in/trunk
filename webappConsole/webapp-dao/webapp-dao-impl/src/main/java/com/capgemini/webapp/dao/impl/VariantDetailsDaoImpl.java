package com.capgemini.webapp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.capgemini.webapp.dao.api.VariantDetailsDao;
import com.capgemini.webapp.dao.api.entity.VariantDetails;

@Repository("variantDetailsDao")
public class VariantDetailsDaoImpl extends AbstractDao<Integer, VariantDetails> implements VariantDetailsDao{

	@Override
	public List<VariantDetails> getVariantDetails(String variantId,String fuelType) {
		
		
		//Criteria crit = createEntityCriteria();
		Criteria crit =  createEntityCriteriawithAlias("variantdetails");
		int variantID = Integer.parseUnsignedInt(variantId);
		//crit.add(Restrictions.eq("variantId", variantID));
		
		crit.createAlias("variantdetails.variant", "categoryvariants");
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid duplicates.
		crit.add(Restrictions.eq("categoryvariants.variantId", variantID));
		crit.add(Restrictions.like("variantdetails.variantDetailsId.fuelType",fuelType));
		
		List<VariantDetails> vd = (List<VariantDetails>) crit.list();
		return vd;
	}
}
