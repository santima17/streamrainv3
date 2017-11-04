package com.tsi2.streamrain.services.session.implementations;

import com.tsi2.streamrain.services.session.interfaces.ISessionService;

public class SessionServiceImpl implements ISessionService{
	
	private String currentTenant;
	private String userNickname;
	
	public String getCurrentTenant() {
		return currentTenant;
	}

	public void setCurrentTenant(final String currentTenant) {
		this.currentTenant = currentTenant;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(final String userNickname) {
		this.userNickname = userNickname;
	}

}
