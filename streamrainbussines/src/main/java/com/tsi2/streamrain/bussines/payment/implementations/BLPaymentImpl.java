package com.tsi2.streamrain.bussines.payment.implementations;

import java.util.List;

import com.tsi2.streamrain.bussines.payment.interfaces.IBLPayment;
import com.tsi2.streamrain.context.StremRainDataContextLoader;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLDAO;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLUserDAO;
import com.tsi2.streamrain.dao.interfaces.IDAOService;
import com.tsi2.streamrain.dao.interfaces.IDAOUserService;
import com.tsi2.streamrain.model.generator.Contents;
import com.tsi2.streamrain.model.generator.PaymentMethods;
import com.tsi2.streamrain.model.generator.UserPpvs;
import com.tsi2.streamrain.model.generator.UserPpvsId;
import com.tsi2.streamrain.model.generator.UserSubscriptions;
import com.tsi2.streamrain.model.generator.Users;

public class BLPaymentImpl implements IBLPayment {
	
	IDAOUserService daoService = (StreamRainMySQLUserDAO) StremRainDataContextLoader.contextLoader().getBean("daoUserService");
	IDAOService daoPaymentMethodService = (StreamRainMySQLDAO) StremRainDataContextLoader.contextLoader().getBean("daoService");
	
	public void saveUserSubscription(UserSubscriptions userSubscriptions, final String nickName, final Integer idPaymentMethod, final String tenantID) {
		Users user = daoService.getUserByNickname(nickName, tenantID);
		PaymentMethods paymentMethods = daoPaymentMethodService.get(PaymentMethods.class, idPaymentMethod, tenantID);
		userSubscriptions.setUsers(user);
		userSubscriptions.setPaymentMethods(paymentMethods);
		daoPaymentMethodService.save(userSubscriptions, tenantID);
	}

	public void saveUserContentPPV(UserPpvs userPPV, final String userNickName, final Integer idContent,
			final Integer idPaymentMethods, final String tenantID) {
		Users user = daoService.getUserByNickname(userNickName, tenantID);
		PaymentMethods paymentMethods = daoPaymentMethodService.get(PaymentMethods.class, idPaymentMethods, tenantID);
		Contents contents = daoPaymentMethodService.get(Contents.class, idContent, tenantID);
		UserPpvsId ppvId = new UserPpvsId();
		ppvId.setContentId(contents.getId());
		ppvId.setUserId(user.getId());
		userPPV.setId(ppvId);
		userPPV.setContents(contents);
		userPPV.setUsers(user);
		userPPV.setPaymentMethods(paymentMethods);
		daoPaymentMethodService.save(userPPV, tenantID);
	}
	
	public List<PaymentMethods> getAllPaytmentMethods(String tenantID){
		return daoPaymentMethodService.getAll(PaymentMethods.class, tenantID);
	}

}
