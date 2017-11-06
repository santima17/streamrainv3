package com.tsi2.streamrain.bussines.user.implementations;

import java.util.List;

import com.tsi2.streamrain.bussines.user.interfaces.IBLUser;
import com.tsi2.streamrain.context.StremRainDataContextLoader;
import com.tsi2.streamrain.dao.interfaces.IDAOService;
import com.tsi2.streamrain.dao.interfaces.IDAOUserService;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLDAO;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLUserDAO;
import com.tsi2.streamrain.model.generator.PaymentMethods;
import com.tsi2.streamrain.model.generator.UserSubscriptions;
import com.tsi2.streamrain.model.generator.Users;

public class BLUserImpl implements IBLUser {

	IDAOUserService daoService = (StreamRainMySQLUserDAO) StremRainDataContextLoader.contextLoader().getBean("daoUserService");
	IDAOService daoPaymentMethodService = (StreamRainMySQLDAO) StremRainDataContextLoader.contextLoader().getBean("daoService");

	public boolean saveUser(final Users u, final String tenantID) {
		daoService.save(u, tenantID);
		return true;
	}

	public List<Users> getAll(final String tenantID) {
		return daoService.getAll(Users.class, tenantID);
	}

	public Users getUserByNickname(final String userNickname, final String tenantID) {
		return daoService.getUserByNickname(userNickname, tenantID);
	}

	public boolean findUserXNickname(final String nickname, final String tenantID) {
		return daoService.findByNickname(nickname, tenantID);
	}

	public boolean findUser(final String nickname, final String password, final String tenantID) {
		return daoService.findByNicknamePassword(nickname, password, tenantID);
	}

	public boolean findUserXTwitterId(final String twitterId, final String tenantID) {
		return daoService.findByTwitterId(twitterId, tenantID);
	}

	public void updateUser(final Users user, final String tenantID) {
		daoService.saveOrUpdate(user, tenantID);
	}

	public void deleteUser(final String userNickname, final String tenantID) {
		Users user = getUserByNickname(userNickname, tenantID);
		// set baja logica true
		daoService.saveOrUpdate(user, tenantID);
	}

	public void saveUserSubscription(UserSubscriptions userSubscriptions, final String nickName, final Integer idPaymentMethod, final String tenantID) {
		Users user = daoService.getUserByNickname(nickName, tenantID);
		PaymentMethods paymentMethods = daoPaymentMethodService.get(PaymentMethods.class, idPaymentMethod, tenantID);
		userSubscriptions.setUsers(user);
		userSubscriptions.setPaymentMethods(paymentMethods);
		daoPaymentMethodService.save(userSubscriptions, tenantID);
	}

}
