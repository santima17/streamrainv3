package com.tsi2.streamrain.dao.interfaces;


public interface IDAOTenantService extends IDAOService{
	
	boolean findByNickname(final String nickname);
	boolean findByNicknamePassword(final String nickname, final String password);

}
