package com.tsi2.streamrain.services.report.implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.tsi2.streamrain.bussines.report.interfaces.IBLReport;
import com.tsi2.streamrain.context.StremRainUserBussinesContextLoader;
import com.tsi2.streamrain.datatypes.report.RateByDateDto;
import com.tsi2.streamrain.datatypes.report.TopFiveDto;
import com.tsi2.streamrain.datatypes.report.UserByCountryDto;
import com.tsi2.streamrain.services.report.interfaces.IReportService;
import com.tsi2.streamrain.bussines.report.implementations.BLReportImpl;

public class ReportServiceImpl implements IReportService{

	IBLReport reportBussines = (BLReportImpl) StremRainUserBussinesContextLoader.contextLoader().getBean("reportBussines");
	
	@Override 
	public List<UserByCountryDto> getFavByMovie(final String tenantID) {
		List<UserByCountryDto> rateList = new ArrayList<UserByCountryDto>();
		HashMap<String, Integer> rateMap = reportBussines.getFavByMovie(tenantID);
		for(Entry<String, Integer> entry : rateMap.entrySet()) {
			UserByCountryDto rateDto = new UserByCountryDto();
		    rateDto.setCountry(entry.getKey());
		    rateDto.setCant(entry.getValue());
		    rateList.add(rateDto);
		}
		return rateList;
	} 
	 
	@Override
	public List<TopFiveDto> topFive(final String tenantID) {
		List<TopFiveDto> top5List = new ArrayList<TopFiveDto>();
		List<Object> rankList = reportBussines.getTopFive(tenantID);
		Iterator<Object> it = rankList.iterator();
		while (it.hasNext()) {
			TopFiveDto top5 = new TopFiveDto();
		    top5.setContentID((Integer)it.next());
		    top5.setRank((Double)it.next());
		    top5List.add(top5);
		}
		return top5List;
	}
	
	@Override
	public List<UserByCountryDto> getUserByCountry(final String tenantID) {
		List<UserByCountryDto> rateList = new ArrayList<UserByCountryDto>();
		HashMap<String, Integer> rateMap = reportBussines.getUsersByCountry(tenantID);
		for(Entry<String, Integer> entry : rateMap.entrySet()) {
			UserByCountryDto userByCountry = new UserByCountryDto();
		    userByCountry.setCountry(entry.getKey());
		    userByCountry.setCant(entry.getValue());
		    rateList.add(userByCountry);
		}
		return rateList;
	} 

}
