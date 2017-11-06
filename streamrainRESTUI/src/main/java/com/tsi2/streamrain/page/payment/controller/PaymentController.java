package com.tsi2.streamrain.page.payment.controller;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tsi2.streamrain.datatypes.paymentmethod.UserSubscriptionDto;
import com.tsi2.streamrain.services.session.interfaces.ISessionService;
import com.tsi2.streamrain.services.user.interfaces.IUserService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Resource(name="userService")
	IUserService userService;
	
	@Resource(name="sessionService")
	ISessionService sessionService;
	
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BindingResult> userSubscription(@RequestBody UserSubscriptionDto userSubscription, BindingResult result) {
    	ResponseEntity<BindingResult> response = new ResponseEntity<>(HttpStatus.CREATED);
    	if (result.hasErrors()) {
    		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    	}
        userService.saveUserSubscription(userSubscription, sessionService.getCurrentTenant());
        return response;
    }

}
