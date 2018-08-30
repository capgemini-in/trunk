/**
 * 
 */
package com.capgemini.webapp.dao.api;

import java.util.List;

import com.capgemini.webapp.dao.api.entity.Dealer;

/**
 * @author vipsatpu
 *
 */
public interface DealerDao {

	public List<Dealer> getDealerByStateCity(int stateId, int cityId);
	
}
