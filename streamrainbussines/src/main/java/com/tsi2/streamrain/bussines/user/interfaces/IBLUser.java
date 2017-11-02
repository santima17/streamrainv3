package com.tsi2.streamrain.bussines.user.interfaces;

import com.tsi2.streamrain.model.generator.Users;

public interface IBLUser {
	
	boolean saveUser(final Users u, final String tenantID);

	boolean findUserXNickname(final String nickname, final String tenantID);

	boolean findUser(final String nickname, final String password, final String tenantID);

	boolean findUserXTwitterId(final String twitterId, final String tenantID);

}
