package com.capgemini.webapp.dao.api;

import java.util.List;

import com.capgemini.webapp.dao.api.entity.Category;
import com.capgemini.webapp.dao.api.entity.CategoryVariants;

public interface CategoryDao {	
	
	public List<Category> getCategoryforMenu(String menuCode,String businessType);
	
}
