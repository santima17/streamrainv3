package com.tsi2.streamraingenerador.security.authentication.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.tsi2.streamrain.services.session.interfaces.ISessionService;
import com.tsi2.streamrain.services.tenants.interfaces.ITenantService;
import com.tsi2.streamrain.services.user.interfaces.IUserService;
import com.tsi2.streamraingenerador.utils.Utils;

public class UserAuthenticationManager implements AuthenticationManager {

	@Autowired
	private IUserService userService;

	@Autowired
	private ITenantService tenantService;

	@Autowired
	private ISessionService sessionService;

	private boolean twitterLogin;

	private String twitterID;

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		String passEncrptyed = Utils.encryptPassword(authentication.getCredentials().toString());
		String userName = authentication.getName();
		boolean user = false;
		if (!twitterLogin) {
			user = userService.existsUser(userName, passEncrptyed, getSessionService().getCurrentTenant());
		} else {
			user = userService.existsUserXTwitterId(twitterID, getSessionService().getCurrentTenant());
		}
		if (!user) {
			throw new BadCredentialsException("1000");
		}
		getSessionService().setUserNickname(userName);
		return new UsernamePasswordAuthenticationToken(userName, passEncrptyed);
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public ITenantService getTenantService() {
		return tenantService;
	}

	public void setTenantService(ITenantService tenantService) {
		this.tenantService = tenantService;
	}

	public boolean isTwitterLogin() {
		return twitterLogin;
	}

	public void setTwitterLogin(boolean twitterLogin) {
		this.twitterLogin = twitterLogin;
	}

	public String getTwitterID() {
		return twitterID;
	}

	public void setTwitterID(String twitterID) {
		this.twitterID = twitterID;
	}

	public ISessionService getSessionService() {
		return sessionService;
	}

	public void setSessionService(ISessionService sessionService) {
		this.sessionService = sessionService;
	}

}
