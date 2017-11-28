package com.tsi2.streamrain.services.payment.interfaces;

import java.util.List;

import com.tsi2.streamrain.datatypes.payment.PaymentMethodDto;
import com.tsi2.streamrain.datatypes.payment.UserContentPPVDto;
import com.tsi2.streamrain.datatypes.payment.UserSubscriptionDto;

public interface IPaymentService {
	
	void saveUserSubscription(UserSubscriptionDto userSubscription, final String tenantID);
	
	void saveUserContentPPV(UserContentPPVDto userContentPPVDto, final String tenantID);

	List<PaymentMethodDto> getAllPaytmentMethods(final String tenantID);

	Long getDaysValidSubscription(final String userNickName, final String tenantID);

	boolean existsPPV(final Integer contentID, final String userNickName, final String tenantID);

}
