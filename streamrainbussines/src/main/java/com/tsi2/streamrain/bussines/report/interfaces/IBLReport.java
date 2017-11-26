package com.tsi2.streamrain.bussines.report.interfaces;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.tsi2.streamrain.model.generator.UserRatings;

public interface IBLReport {
	
	HashMap<Date, Integer> getRankByDate(final Integer contentID, final Date dateFrom, final Date dateTo, final String tenantID);
	List<Object> getTopFive(final String tenantID);
	HashMap<String, Integer> getUsersByCountry(final String tenantID);
	
}
