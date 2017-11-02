package com.tsi2.streamrain.services.user.implementations;


import com.tsi2.streamrain.bussines.user.interfaces.IBLUser;
import com.tsi2.streamrain.bussines.user.implementations.BLUserImpl;
import com.tsi2.streamrain.context.StremRainFacadesContextLoader;
import com.tsi2.streamrain.context.StremRainUserBussinesContextLoader;
import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.converters.user.UserConverter;

import com.tsi2.streamrain.datatypes.user.UserDto;
import com.tsi2.streamrain.model.generator.Users;
import com.tsi2.streamrain.services.user.interfaces.IUserService;

public class UserServiceImpl implements IUserService{

	public boolean saveUser(final UserDto u, final String tenantID) {
		IBLUser blService = (BLUserImpl) StremRainUserBussinesContextLoader.contextLoader().getBean("userBussines");
		IConverter userConverter = (UserConverter)StremRainFacadesContextLoader.contextLoader().getBean("userConverter");
		blService.saveUser((Users)userConverter.deConverter(u), tenantID);
		return true;
	}

	public boolean existsUserXNickName(String nickname, String tenantID) {
		IBLUser blService = (BLUserImpl) StremRainUserBussinesContextLoader.contextLoader().getBean("userBussines");
		return blService.findUserXNickname(nickname, tenantID);
	}

	public boolean existsUser(String nickname, String password, String tenantID) {
		IBLUser blService = (BLUserImpl) StremRainUserBussinesContextLoader.contextLoader().getBean("userBussines");
		return blService.findUser(nickname, password, tenantID);
	}

	public boolean existsUserXTwitterId(String twitterId, String tenantID) {
		IBLUser blService = (BLUserImpl) StremRainUserBussinesContextLoader.contextLoader().getBean("userBussines");
		return blService.findUserXTwitterId(twitterId, tenantID);
	}

	
		
}
