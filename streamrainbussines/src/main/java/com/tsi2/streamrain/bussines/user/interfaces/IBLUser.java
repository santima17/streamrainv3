package com.tsi2.streamrain.bussines.user.interfaces;

import java.util.List;

import com.tsi2.streamrain.model.generator.UserSubscriptions;
import com.tsi2.streamrain.model.generator.Users;

public interface IBLUser {
	
	boolean saveUser(final Users u, final String tenantID);
	
	List<Users> getAll(final String tenantID);
	
	Users getUserByNickname(final String userNickname, final String tenantID);
	
	boolean findUserXNickname(final String nickname, final String tenantID);

	boolean findUser(final String nickname, final String password, final String tenantID);

	boolean findUserXTwitterId(final String twitterId, final String tenantID);

	void updateUser(final Users deConverter, final String currentTenant);

	void deleteUser(final String userNickname, final String currentTenant);

	void saveUserSubscription(UserSubscriptions userSubscriptions, final String nickName, final Integer idPaymentMethod, final String tenantID);

}
