/**
 * 
 */
package com.capgemini.webapp.dao.api;

import java.util.List;

import com.capgemini.webapp.dao.api.entity.BusinessMenu;

/**
 * @author vipsatpu
 *
 */
public interface MenuDao {

	public List<BusinessMenu> getAllBusinessMenus();
}
