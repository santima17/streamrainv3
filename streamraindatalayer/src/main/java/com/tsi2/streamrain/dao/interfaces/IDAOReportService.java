package com.tsi2.streamrain.dao.interfaces;

import java.util.Date;
import java.util.List;

import com.tsi2.streamrain.model.generator.UserRatings;

public interface IDAOReportService extends IDAOService{
	
	List getRankByDate(final Integer contentID, final Date dateFrom, final Date dateTo, final String tenantID);
	List getTopFive(final String tenantID);
	List getUsersByCountry(final String tenantID);

}
