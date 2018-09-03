package com.capgemini.webapp.dao.api;

import java.util.List;

import com.capgemini.webapp.dao.api.entity.VariantDetails;

public interface VariantDetailsDao {

	public List<VariantDetails> getVariantDetails(String variantId, String fuelType);
}
