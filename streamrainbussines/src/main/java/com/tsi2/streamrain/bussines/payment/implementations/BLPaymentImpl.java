package com.tsi2.streamrain.bussines.payment.implementations;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.tsi2.streamrain.bussines.payment.interfaces.IBLPayment;
import com.tsi2.streamrain.context.StremRainDataContextLoader;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLContentDAO;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLPaymentDAO;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLUserDAO;
import com.tsi2.streamrain.dao.interfaces.IDAOContentService;
import com.tsi2.streamrain.dao.interfaces.IDAOPaymentService;
import com.tsi2.streamrain.dao.interfaces.IDAOUserService;
import com.tsi2.streamrain.model.generator.Contents;
import com.tsi2.streamrain.model.generator.PaymentMethods;
import com.tsi2.streamrain.model.generator.UserPpvs;
import com.tsi2.streamrain.model.generator.UserPpvsId;
import com.tsi2.streamrain.model.generator.UserSubscriptions;
import com.tsi2.streamrain.model.generator.Users;

public class BLPaymentImpl implements IBLPayment {

	IDAOUserService daoService = (StreamRainMySQLUserDAO) StremRainDataContextLoader.contextLoader()
			.getBean("daoUserService");
	IDAOPaymentService daoPaymentMethodService = (StreamRainMySQLPaymentDAO) StremRainDataContextLoader.contextLoader()
			.getBean("daoPaymentService");
	IDAOContentService daoContentService = (StreamRainMySQLContentDAO) StremRainDataContextLoader.contextLoader()
			.getBean("daoContentService");

	public void saveUserSubscription(UserSubscriptions userSubscriptions, final String nickName,
			final Integer idPaymentMethod, final String tenantID) {
		Users user = daoService.getUserByNickname(nickName, tenantID);
		PaymentMethods paymentMethods = daoPaymentMethodService.get(PaymentMethods.class, idPaymentMethod, tenantID);
		userSubscriptions.setUsers(user);
		userSubscriptions.setJanusUserToken(UUID.randomUUID().toString());
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

	public List<PaymentMethods> getAllPaytmentMethods(String tenantID) {
		return daoPaymentMethodService.getAll(PaymentMethods.class, tenantID);
	}

	public Long getDaysValidSubscription(final String userNickName, final String tenantID) {
		try {
			Users user = daoService.getUserByNickname(userNickName, tenantID);
			Date now = new Date();
			Integer id = daoPaymentMethodService.existeValidSubscription(user.getId(), now, tenantID);
			if (id != null) {
				UserSubscriptions subscription = daoService.get(UserSubscriptions.class, id, tenantID);
				if (subscription != null) {
					Date dataFinish = subscription.getDateFinish();
					return dataFinish.getTime() - now.getTime();
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean existsPPV(final Integer contentID, final String userNickName, final String tenantID) {
		Users user = daoService.getUserByNickname(userNickName, tenantID);
		Contents contents = daoContentService.get(Contents.class, contentID, tenantID);
		UserPpvs userPpvs = new UserPpvs();
		userPpvs.setContents(contents);
		userPpvs.setUsers(user);
		List<UserPpvs> list = daoPaymentMethodService.getAllByExample(UserPpvs.class, userPpvs, tenantID);
		return !list.isEmpty();
	}

}
