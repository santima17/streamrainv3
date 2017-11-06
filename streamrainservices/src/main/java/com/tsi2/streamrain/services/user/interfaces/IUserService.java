package com.tsi2.streamrain.services.user.interfaces;

import java.util.List;

import com.tsi2.streamrain.datatypes.user.UserDto;

public interface IUserService {
	
	boolean saveUser(final UserDto u, final String tenantID);
	
	List<UserDto> getAll(final String tenantID);
	
	UserDto getUserByNickname(final String userNickname, final String tenantID);
	
	boolean existsUser(final String nickname, final String password, final String tenantID);
	
	boolean existsUserXNickName (final String nickname, final String tenantID);
	
	boolean existsUserXTwitterId (final String twitterId, final String tenantID);

	void updateUser(final String userNickname, final UserDto user, final String currentTenant);
	
	void deleteUser(final String userNickname, final String currentTenant);

}
