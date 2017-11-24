package com.tsi2.streamrain.page.payment.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
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

	private static final String MENSUAL = "mensual";

	private static final String SEMANAL = "semanal";

	@Resource(name = "paymentService")
	IPaymentService paymentService;

	@Resource(name = "sessionService")
	ISessionService sessionService;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PaymentMethodDto> getAllPaytmentMethods() {
		return (List<PaymentMethodDto>) paymentService.getAllPaytmentMethods(sessionService.getCurrentTenant());
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<BindingResult> userSubscription(@RequestBody UserSubscriptionDto userSubscription,
			BindingResult result) {
		ResponseEntity<BindingResult> response = new ResponseEntity<>(HttpStatus.CREATED);
		if (result.hasErrors()) {
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		paymentService.saveUserSubscription(userSubscription, sessionService.getCurrentTenant());
		return response;
	}

	@RequestMapping(value = "/paypal/{ordernumber}/{userNickname}/{suscriptionType}", method = RequestMethod.POST)
	public HttpStatus paypal(@PathVariable String ordernumber, @PathVariable String userNickname,
			@PathVariable String suscriptionType, HttpServletRequest request) {
    	String url = request.getRequestURL().toString();
		String tentantID = url.substring(7,url.indexOf("."));
		UserSubscriptionDto userSubscription = new UserSubscriptionDto();
		Date today = GregorianCalendar.getInstance().getTime();
		Calendar calendar = GregorianCalendar.getInstance();
		if (SEMANAL.equals(suscriptionType)) {
			calendar.add(GregorianCalendar.DAY_OF_YEAR, 7);
			userSubscription.setDateFinish(calendar.getTime());
		} else if (MENSUAL.equals(suscriptionType)) {
			calendar.add(GregorianCalendar.DAY_OF_YEAR, 30);
			userSubscription.setDateFinish(calendar.getTime());
		} else {
			calendar.add(GregorianCalendar.DAY_OF_YEAR, 365);
			userSubscription.setDateFinish(calendar.getTime());
		}
		userSubscription.setDateStart(today);
		userSubscription.setIdPaymentMethod(1);
		userSubscription.setUserNickName(userNickname);
		userSubscription.setDatePayment(today);
		paymentService.saveUserSubscription(userSubscription, tentantID);
		return HttpStatus.ACCEPTED;
	}

}
