package com.tsi2.streamrain.dao.interfaces;

import java.util.List;


public interface IDAOReportService extends IDAOService{
	
	List getFavByMovie(final String tenantID);
	List getTopFive(final String tenantID);
	List getUsersByCountry(final String tenantID);

}
