package com.tsi2.streamrain.services.user.implementations;


import com.tsi2.streamrain.bussines.user.interfaces.IBLUser;

import java.util.List;

import com.tsi2.streamrain.bussines.user.implementations.BLUserImpl;
import com.tsi2.streamrain.context.StremRainFacadesContextLoader;
import com.tsi2.streamrain.context.StremRainUserBussinesContextLoader;
import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.converters.payment.PaymentConverter;
import com.tsi2.streamrain.converters.user.UserConverter;
import com.tsi2.streamrain.datatypes.paymentmethod.UserSubscriptionDto;
import com.tsi2.streamrain.datatypes.user.UserDto;
import com.tsi2.streamrain.model.generator.UserSubscriptions;
import com.tsi2.streamrain.model.generator.Users;
import com.tsi2.streamrain.services.user.interfaces.IUserService;

public class UserServiceImpl implements IUserService{

	IConverter<UserDto, Users> userConverter = (UserConverter) StremRainFacadesContextLoader.contextLoader().getBean("userConverter");
	IConverter<UserSubscriptionDto, UserSubscriptions> paymentConverter = (PaymentConverter) StremRainFacadesContextLoader.contextLoader().getBean("paymentConverter");
	IBLUser userBussines = (BLUserImpl) StremRainUserBussinesContextLoader.contextLoader().getBean("userBussines");
	
	public boolean saveUser(final UserDto u, final String tenantID) {
		userBussines.saveUser((Users)userConverter.deConverter(u), tenantID);
		return true;
	}
	
	public List<UserDto> getAll(final String tenantID) {
		return userConverter.convertAll(userBussines.getAll(tenantID));
	}
	
	public UserDto getUserByNickname(final String userNickname, final String tenantID) {
		return userConverter.converter(userBussines.getUserByNickname(userNickname, tenantID));
	}
	
	public boolean existsUserXNickName(final String nickname, final String tenantID) {
		return userBussines.findUserXNickname(nickname, tenantID);
	}

	public boolean existsUser(final String nickname, final String password, final String tenantID) {
		return userBussines.findUser(nickname, password, tenantID);
	}

	public boolean existsUserXTwitterId(final String twitterId, final String tenantID) {
		return userBussines.findUserXTwitterId(twitterId, tenantID);
	}

	public void updateUser(final String userNickname, final UserDto user, final String tenantID) {
		userBussines.updateUser((Users)userConverter.deConverter(user), tenantID);
	}

	public void deleteUser(final String userNickname, final String tenantID) {
		userBussines.deleteUser(userNickname, tenantID);
	}

	public void saveUserSubscription(UserSubscriptionDto userSubscription, String tenantID) {
		userBussines.saveUserSubscription((UserSubscriptions)paymentConverter.deConverter(userSubscription), userSubscription.getUserNickName(), userSubscription.getIdPaymentMethod(), tenantID);	
	}



}
