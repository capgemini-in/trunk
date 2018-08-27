package com.capgemini.webapp.dao.api;

import java.util.List;

import com.capgemini.webapp.dao.api.entity.Category;

public interface CategoryDao {
	
	public List<Category> getCategoryforMenu(String menuCode,String businessType);
	
	
}
