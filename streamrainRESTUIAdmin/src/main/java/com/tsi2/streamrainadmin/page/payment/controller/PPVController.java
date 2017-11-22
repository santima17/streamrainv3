package com.tsi2.streamrainadmin.page.payment.controller;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tsi2.streamrain.datatypes.payment.UserContentPPVDto;
import com.tsi2.streamrain.services.payment.interfaces.IPaymentService;
import com.tsi2.streamrain.services.session.interfaces.ISessionService;

@RestController
@RequestMapping("/user/payment/ppv")
public class PPVController {
	
	@Resource(name="paymentService")
	IPaymentService paymentService;
	
	@Resource(name="sessionService")
	ISessionService sessionService;
	
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BindingResult> userContentPPV(@RequestBody UserContentPPVDto userContentPPVDto, BindingResult result) {
    	ResponseEntity<BindingResult> response = new ResponseEntity<>(HttpStatus.CREATED);
    	if (result.hasErrors()) {
    		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    	}
    	sessionService.setCurrentTenant("generator1");
    	paymentService.saveUserContentPPV(userContentPPVDto, sessionService.getCurrentTenant());
        return response;
    }

}
