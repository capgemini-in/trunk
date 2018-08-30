package com.capgemini.webapp.service.api;

import java.util.List;

import com.capgemini.webapp.service.api.model.DealerModel;

public interface DealerService {
	public List<DealerModel> getAllDealerforStateCity(String stateId, String cityId);
}
