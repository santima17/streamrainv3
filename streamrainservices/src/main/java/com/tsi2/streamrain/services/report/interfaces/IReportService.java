package com.tsi2.streamrain.services.report.interfaces;

import java.util.Date;
import java.util.List;

import com.tsi2.streamrain.datatypes.report.RateByDateDto;
import com.tsi2.streamrain.datatypes.report.TopFiveDto;
import com.tsi2.streamrain.datatypes.report.UserByCountryDto;

public interface IReportService {
	
	List<RateByDateDto> rateList(final Integer contentID, final Date dateFrom, final Date dateTo, final String tenantID);
	List<TopFiveDto> topFive(final String tenantID);
	List<UserByCountryDto> getUserByCountry(final String tenantID);
}
