package com.capgemini.webapp.service.api;

import java.util.List;

import com.capgemini.webapp.service.api.model.VariantDetailsModel;

public interface VariantDetailsService {

	public List<VariantDetailsModel> getVariantDetails(String variantId, String fuelType);
}
