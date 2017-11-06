package com.tsi2.streamrain.services.payment.interfaces;

import java.util.List;

import com.tsi2.streamrain.datatypes.payment.PaymentMethodDto;
import com.tsi2.streamrain.datatypes.payment.UserContentPPVDto;
import com.tsi2.streamrain.datatypes.payment.UserSubscriptionDto;

public interface IPaymentService {
	
	void saveUserSubscription(UserSubscriptionDto userSubscription, final String currentTenant);
	
	void saveUserContentPPV(UserContentPPVDto userContentPPVDto, final String currentTenant);

	List<PaymentMethodDto> getAllPaytmentMethods(final String currentTenant);

}
