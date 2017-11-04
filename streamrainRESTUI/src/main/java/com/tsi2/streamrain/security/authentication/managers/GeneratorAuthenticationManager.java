package com.tsi2.streamrain.security.authentication.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.tsi2.streamrain.services.session.interfaces.ISessionService;
import com.tsi2.streamrain.services.tenants.interfaces.ITenantService;
import com.tsi2.streamrain.utils.Utils;


public class GeneratorAuthenticationManager implements AuthenticationManager{

	@Autowired
	ITenantService tenantService;
	
	@Autowired
	private ISessionService sessionService;
	
	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		String passEncrptyed = Utils.encryptPassword(authentication.getCredentials().toString());
		String userName = authentication.getName();
		boolean user = getTenantService().existsUser(userName, passEncrptyed);
	    if (!user) {
	        throw new BadCredentialsException("1000");
	    }
	    getSessionService().setUserNickname(userName);
	    return new UsernamePasswordAuthenticationToken(userName, passEncrptyed);
	}

	public ITenantService getTenantService() {
		return tenantService;
	}

	public void setTenantService(ITenantService tenantService) {
		this.tenantService = tenantService;
	}

	public ISessionService getSessionService() {
		return sessionService;
	}

	public void setSessionService(ISessionService sessionService) {
		this.sessionService = sessionService;
	}
	
	
	
	

	

}
 