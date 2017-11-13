package com.tsi2.streamrain.dao.interfaces;

import com.tsi2.streamrain.model.main.Tenants;

public interface IDAOTenantService extends IDAOService{
	
	boolean findByNickname(final String nickname);
	boolean findByNicknamePassword(final String nickname, final String password);


}
