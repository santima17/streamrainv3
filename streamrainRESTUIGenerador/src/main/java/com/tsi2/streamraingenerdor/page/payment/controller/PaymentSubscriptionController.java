package com.tsi2.streamraingenerdor.page.payment.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tsi2.streamrain.datatypes.payment.PaymentMethodDto;
import com.tsi2.streamrain.datatypes.payment.UserSubscriptionDto;
import com.tsi2.streamrain.services.payment.interfaces.IPaymentService;
import com.tsi2.streamrain.services.session.interfaces.ISessionService;

@RestController
@RequestMapping("/user/payment/subscription")
public class PaymentSubscriptionController {
	
	@Resource(name="paymentService")
	IPaymentService paymentService;
	
	@Resource(name="sessionService")
	ISessionService sessionService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PaymentMethodDto> getAllPaytmentMethods() {
		//sessionService.setCurrentTenant("generator1");
        return (List<PaymentMethodDto>) paymentService.getAllPaytmentMethods(sessionService.getCurrentTenant());
    }
	
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BindingResult> userSubscription(@RequestBody UserSubscriptionDto userSubscription, BindingResult result) {
    	ResponseEntity<BindingResult> response = new ResponseEntity<>(HttpStatus.CREATED);
    	if (result.hasErrors()) {
    		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    	}
    	//sessionService.setCurrentTenant("generator1");
    	paymentService.saveUserSubscription(userSubscription, sessionService.getCurrentTenant());
        return response;
    }

}
