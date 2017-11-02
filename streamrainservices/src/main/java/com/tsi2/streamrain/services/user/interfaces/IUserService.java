package com.tsi2.streamrain.services.user.interfaces;

import com.tsi2.streamrain.datatypes.user.UserDto;

public interface IUserService {
	
	boolean saveUser(final UserDto u, final String tenantID);
	
	boolean existsUser(final String nickname, final String password, final String tenantID);
	
	boolean existsUserXNickName (final String nickname, final String tenantID);
	
	boolean existsUserXTwitterId (final String twitterId, final String tenantID);

}
