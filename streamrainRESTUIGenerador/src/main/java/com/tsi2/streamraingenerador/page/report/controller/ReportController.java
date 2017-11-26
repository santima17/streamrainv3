package com.tsi2.streamraingenerador.page.report.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tsi2.streamrain.datatypes.report.RateByDateDto;
import com.tsi2.streamrain.datatypes.report.TopFiveDto;
import com.tsi2.streamrain.datatypes.report.UserByCountryDto;
import com.tsi2.streamrain.datatypes.tenant.TenantDto;
import com.tsi2.streamrain.services.report.interfaces.IReportService;
import com.tsi2.streamrain.services.session.interfaces.ISessionService;

@RestController
@RequestMapping("/report")
public class ReportController {

	@Autowired
	IReportService reportService;

	@Autowired
	ISessionService sessionService;

	@RequestMapping(value = "/getRateByDate", method = RequestMethod.POST)
    public ResponseEntity<List<RateByDateDto>> getRateByDate(@RequestParam("contentID") Integer contentID, 
    		@RequestParam("dateFrom") String dateFrom, @RequestParam("dateTo") String dateTo, HttpServletRequest request) throws ParseException {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		
		List<RateByDateDto> list = reportService.rateList(contentID, formater.parse(dateFrom), formater.parse(dateTo), sessionService.getCurrentTenant());
		if(list != null && !list.isEmpty()) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/getTopFive", method = RequestMethod.GET)
    public ResponseEntity<List<TopFiveDto>> getTopFive() throws ParseException {
		
		List<TopFiveDto> list = reportService.topFive(sessionService.getCurrentTenant());
		if(list != null && !list.isEmpty()) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/getUserByCountry", method = RequestMethod.GET)
    public ResponseEntity<List<UserByCountryDto>> getUserByCountry() throws ParseException {
		
		List<UserByCountryDto> list = reportService.getUserByCountry(sessionService.getCurrentTenant());
		if(list != null && !list.isEmpty()) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
