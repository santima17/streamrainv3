package com.tsi2.streamrain.dao.interfaces;

public interface IDAOUserService extends IDAOService {
	
	public boolean findByNickname(final String nickname, final String tenantID);

	public boolean findByNicknamePassword(String nickname, String password, String tenantID);

	public boolean findByTwitterId(String twitterId, String tenantID);

}
