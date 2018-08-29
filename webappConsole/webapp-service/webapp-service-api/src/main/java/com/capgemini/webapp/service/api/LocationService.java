package com.capgemini.webapp.service.api;
import java.util.List;

import com.capgemini.webapp.service.api.model.CategoryModel;
import com.capgemini.webapp.service.api.model.CountryModel;
import com.capgemini.webapp.service.api.model.SubMenuCategoryModel;

public interface LocationService {

	public List<CountryModel> getAllCountries();
	public List<CategoryModel> getCategoryDetail(String subMenuId, String businessTypeId);
	public SubMenuCategoryModel getVariantsForCategory(String subMenuId);
	
}
