package com.capgemini.webapp.dao.api;

import com.capgemini.webapp.dao.api.entity.BusinessSubMenu;

public interface BusinessSubMenuDao {

	public BusinessSubMenu getVariantsForCategory(String subMenuId);
}
