package com.tsi2.streamrain.converters.payment;

import java.util.List;

import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.datatypes.paymentmethod.UserSubscriptionDto;
import com.tsi2.streamrain.model.generator.UserSubscriptions;


public class PaymentConverter implements IConverter<UserSubscriptionDto, UserSubscriptions>{
		
	public UserSubscriptionDto converter(UserSubscriptions source) {
		return null;
	}

	public UserSubscriptions deConverter(UserSubscriptionDto source) {
		UserSubscriptions userSubscriptions = new UserSubscriptions();
		userSubscriptions.setDatePayment(source.getDatePayment());
		userSubscriptions.setDateStart(source.getDateStart());
		userSubscriptions.setDateFinish(source.getDateFinish());
		return userSubscriptions;
	}

	public List<UserSubscriptionDto> convertAll(List<UserSubscriptions> source) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserSubscriptions> deConvertAll(List<UserSubscriptionDto> source) {
		// TODO Auto-generated method stub
		return null;
	}

}
