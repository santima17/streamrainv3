package com.tsi2.streamrainadmin.security.authentication.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.tsi2.streamrain.services.session.interfaces.ISessionService;
import com.tsi2.streamrainadmin.utils.Utils;

public class AdministratorAuthenticationManager implements AuthenticationManager {
	
	@Value("${admin.user}")
	private String storedUser;
	
	@Value("${admin.password}") 
	private String storedPass;
	
	@Autowired
	private ISessionService sessionService;

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		String passEncrptyed = Utils.encryptPassword(authentication.getCredentials().toString());
		String userName = authentication.getName();
	    //boolean user = userService.existsUser(userName, passEncrptyed, getTenantService().getCurrentTenant());
	    boolean user = userName.equals(storedUser) && passEncrptyed.equals(storedPass);
	    if (!user) {
	        throw new BadCredentialsException("1000");
	    }
	    getSessionService().setUserNickname(userName);
	    return new UsernamePasswordAuthenticationToken(userName, passEncrptyed);
	}
	
	public ISessionService getSessionService() {
		return sessionService;
	}

	public void setSessionService(ISessionService sessionService) {
		this.sessionService = sessionService;
	}

}
