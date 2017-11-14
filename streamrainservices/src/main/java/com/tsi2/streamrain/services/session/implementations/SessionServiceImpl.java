package com.tsi2.streamrain.services.session.implementations;

import com.tsi2.streamrain.services.session.interfaces.ISessionService;

public class SessionServiceImpl implements ISessionService{
	
	private String currentTenant;
	private String userNickname;
	private String mySession;
	private String mySessionHandler;
	private String myTextroomHandle;
	
	
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

	public String getMySession() {
		return mySession;
	}

	public void setMySession(String mySession) {
		this.mySession = mySession;
	}

	public String getMySessionHandler() {
		return mySessionHandler;
	}

	public void setMySessionHandler(String mySessionHandler) {
		this.mySessionHandler = mySessionHandler;
	}

	public String getMyTextroomHandle() {
		return myTextroomHandle;
	}

	public void setMyTextroomHandle(String myTextroomHandle) {
		this.myTextroomHandle = myTextroomHandle;
	}
	
	

}
