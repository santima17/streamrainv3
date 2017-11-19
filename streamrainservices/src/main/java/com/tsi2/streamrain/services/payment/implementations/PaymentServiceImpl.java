package com.tsi2.streamrain.services.payment.implementations;

import java.util.List;

import com.tsi2.streamrain.bussines.payment.implementations.BLPaymentImpl;
import com.tsi2.streamrain.bussines.payment.interfaces.IBLPayment;
import com.tsi2.streamrain.context.StremRainFacadesContextLoader;
import com.tsi2.streamrain.context.StremRainUserBussinesContextLoader;
import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.converters.payment.PaymentMethodConverter;
import com.tsi2.streamrain.converters.payment.PaymentPPVConverter;
import com.tsi2.streamrain.converters.payment.SubscribePaymentConverter;
import com.tsi2.streamrain.datatypes.payment.PaymentMethodDto;
import com.tsi2.streamrain.datatypes.payment.UserContentPPVDto;
import com.tsi2.streamrain.datatypes.payment.UserSubscriptionDto;
import com.tsi2.streamrain.model.generator.PaymentMethods;
import com.tsi2.streamrain.model.generator.UserPpvs;
import com.tsi2.streamrain.model.generator.UserSubscriptions;
import com.tsi2.streamrain.services.payment.interfaces.IPaymentService;

public class PaymentServiceImpl implements IPaymentService {

	IConverter<UserSubscriptionDto, UserSubscriptions> subscribePaymentConverter = (SubscribePaymentConverter) StremRainFacadesContextLoader.contextLoader().getBean("subscribePaymentConverter");
	IConverter<UserContentPPVDto, UserPpvs> paymentPPVConverter = (PaymentPPVConverter) StremRainFacadesContextLoader.contextLoader().getBean("paymentPPVConverter");
	IConverter<PaymentMethodDto, PaymentMethods> paymentMethodConverter = (PaymentMethodConverter) StremRainFacadesContextLoader.contextLoader().getBean("paymentMethodConverter");
	IBLPayment paymentBussines = (BLPaymentImpl) StremRainUserBussinesContextLoader.contextLoader().getBean("paymentBussines");
	
	public void saveUserSubscription(UserSubscriptionDto userSubscription, final String tenantID) {
		paymentBussines.saveUserSubscription((UserSubscriptions)subscribePaymentConverter.deConverter(userSubscription), userSubscription.getUserNickName(), userSubscription.getIdPaymentMethod(), tenantID);
	}

	public void saveUserContentPPV(UserContentPPVDto userContentPPVDto, final String tenantID) {
		paymentBussines.saveUserContentPPV((UserPpvs)paymentPPVConverter.deConverter(userContentPPVDto), userContentPPVDto.getUserNickName(), userContentPPVDto.getIdContent(), userContentPPVDto.getIdPaymentMethods(), tenantID);
	}
	
	public List<PaymentMethodDto> getAllPaytmentMethods(final String tenantID){
		List<PaymentMethods> list = paymentBussines.getAllPaytmentMethods(tenantID);
		return paymentMethodConverter.convertAll(list);
	}

	public Long getDaysValidSubscription(final String userNickName, final String tenantID) {
		return paymentBussines.getDaysValidSubscription(userNickName, tenantID);
	}

}
