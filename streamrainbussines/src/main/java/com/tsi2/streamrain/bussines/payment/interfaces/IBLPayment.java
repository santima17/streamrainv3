package com.tsi2.streamrain.bussines.payment.interfaces;

import java.util.List;

import com.tsi2.streamrain.model.generator.PaymentMethods;
import com.tsi2.streamrain.model.generator.UserPpvs;
import com.tsi2.streamrain.model.generator.UserSubscriptions;

public interface IBLPayment {

	void saveUserSubscription(UserSubscriptions userSubscriptions, final String nickName, final Integer idPaymentMethod, final String tenantID);

	void saveUserContentPPV(UserPpvs userPPV, final String userNickName, final Integer idContent, final Integer idPaymentMethods,
			final String tenantID);

	List<PaymentMethods> getAllPaytmentMethods(final String tenantID);

	Long getDaysValidSubscription(final String userNickName, final String tenantID);

	boolean existsPPV(final Integer contentID, final String userNickName, final String tenantID);

}
