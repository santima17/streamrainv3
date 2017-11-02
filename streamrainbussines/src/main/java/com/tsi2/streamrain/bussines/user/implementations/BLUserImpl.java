package com.tsi2.streamrain.bussines.user.implementations;

import com.tsi2.streamrain.bussines.user.interfaces.IBLUser;
import com.tsi2.streamrain.context.StremRainDataContextLoader;
import com.tsi2.streamrain.dao.interfaces.IDAOUserService;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLUserDAO;
import com.tsi2.streamrain.model.generator.Users;

public class BLUserImpl implements IBLUser{

	public boolean saveUser(final Users u, final String tenantID) {
		IDAOUserService daoService = (StreamRainMySQLUserDAO) StremRainDataContextLoader.contextLoader().getBean("daoUserService");
		daoService.save(u, tenantID);
		return true; 
	}

	public boolean findUserXNickname(final String nickname, final String tenantID) {
		IDAOUserService daoService = (StreamRainMySQLUserDAO) StremRainDataContextLoader.contextLoader().getBean("daoUserService");
		return daoService.findByNickname(nickname, tenantID); 
	}

	public boolean findUser(final String nickname, final String password, final String tenantID) {
		IDAOUserService daoService = (StreamRainMySQLUserDAO) StremRainDataContextLoader.contextLoader().getBean("daoUserService");
		return daoService.findByNicknamePassword(nickname, password, tenantID); 
	}

	public boolean findUserXTwitterId(final String twitterId, final String tenantID) {
		IDAOUserService daoService = (StreamRainMySQLUserDAO) StremRainDataContextLoader.contextLoader().getBean("daoUserService");
		return daoService.findByTwitterId(twitterId, tenantID); 
	}

	

}
