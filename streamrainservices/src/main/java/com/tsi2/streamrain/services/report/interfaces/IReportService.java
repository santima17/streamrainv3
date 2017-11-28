package com.tsi2.streamrain.services.report.interfaces;

import java.util.List;

import com.tsi2.streamrain.datatypes.report.TopFiveDto;
import com.tsi2.streamrain.datatypes.report.UserByCountryDto;

public interface IReportService {
	 
	List<UserByCountryDto> getFavByMovie(final String tenantID);
	List<TopFiveDto> topFive(final String tenantID);
	List<UserByCountryDto> getUserByCountry(final String tenantID);
}
