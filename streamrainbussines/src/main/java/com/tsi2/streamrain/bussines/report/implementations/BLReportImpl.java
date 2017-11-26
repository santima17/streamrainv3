package com.tsi2.streamrain.bussines.report.implementations;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.tsi2.streamrain.bussines.report.interfaces.IBLReport;
import com.tsi2.streamrain.context.StremRainDataContextLoader;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLReportDAO;
import com.tsi2.streamrain.dao.interfaces.IDAOReportService;

public class BLReportImpl implements IBLReport{

	IDAOReportService daoService = (StreamRainMySQLReportDAO) StremRainDataContextLoader.contextLoader().getBean("daoReportService");
	
	public HashMap<Date, Integer> getRankByDate(final Integer contentID, final Date dateFrom, final Date dateTo, final String tenantID) {
		
		List<Object[]> list = daoService.getRankByDate(contentID, dateFrom, dateTo, tenantID);
		HashMap<Date, Integer> rateMap = new HashMap<Date, Integer>();
		for(Object[] ur : list) {
			rateMap.put((Date)ur[2],(Integer)ur[3]);
		}
		return rateMap;
	}
	
	public List<Object> getTopFive(final String tenantID) { 
		
		List<Object[]> list = daoService.getTopFive(tenantID);
		List<Object> rateMap = new ArrayList<Object>();
		for(Object[] ur : list) {
			rateMap.add((Integer)ur[0]);
			rateMap.add((Double)ur[4]);
		}
		return rateMap;
	}
	
	public HashMap<String, Integer> getUsersByCountry(final String tenantID) { 
		
		List<Object[]> list = daoService.getUsersByCountry(tenantID);
		HashMap<String, Integer>  rateMap = new HashMap<String, Integer>();
		for(Object[] ur : list) {
			rateMap.put((String)ur[1],((BigInteger)ur[0]).intValue());
		}
		return rateMap;
	}

	
	
}
