package com.tsi2.streamrain.bussines.user.implementations;

import java.util.List;

import com.tsi2.streamrain.bussines.user.interfaces.IBLUser;
import com.tsi2.streamrain.context.StremRainDataContextLoader;
import com.tsi2.streamrain.dao.interfaces.IDAOUserService;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLUserDAO;
import com.tsi2.streamrain.model.generator.Users;

public class BLUserImpl implements IBLUser {

	IDAOUserService daoService = (StreamRainMySQLUserDAO) StremRainDataContextLoader.contextLoader().getBean("daoUserService");
	
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

	public void updateUser(final Users user, Users userOld, final String tenantID) {
		userOld.setCity(user.getCity());
		userOld.setCountry(user.getCountry());
		userOld.setEmail(user.getEmail());
		userOld.setIsBanned(user.getIsBanned());
		if(user.getPassword() != null) {
			userOld.setPassword(user.getPassword());
		}
		userOld.setNickname(user.getNickname());
		userOld.setFacebookUserId(user.getFacebookUserId());
		userOld.setId(user.getId());
		userOld.setIntegratorId(user.getIntegratorId());
		userOld.setTwitterUserId(user.getTwitterUserId());
		userOld.setBlocked(user.isBlocked());
		daoService.saveOrUpdate(userOld, tenantID);
	} 

	public void deleteUser(Users userOld, final String tenantID) {
		userOld.setBlocked(true);
		daoService.saveOrUpdate(userOld, tenantID);
	}

}
