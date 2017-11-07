package com.tsi2.streamrain.services.session.interfaces;

public interface ISessionService {
	
	String getCurrentTenant();
	void setCurrentTenant(final String currentTenant);
	String getUserNickname();
	void setUserNickname(final String nickname);
}
