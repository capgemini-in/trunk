package com.capgemini.webapp.service.api;
import java.util.List;
import com.capgemini.webapp.service.api.model.CountryModel;

public interface LocationService {

	public List<CountryModel> getAllCountries();
	
}
