package com.tsi2.streamrain.security.authentication.managers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class AdministratorAuthenticationManager implements AuthenticationManager {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = authentication.getName().trim();
		String password = authentication.getCredentials().toString().trim();
		Authentication auth = null;
		// String role = login.getApplicationRole(userName, password,
		// "ADMIN","DEVELOPER"); ESTO ES EL LOGIN CUSTOM Autoriza y retorna los roles
		// if (role != null){
		// Collection<GrantedAuthority> grantedAuths = new
		// SimpleGrantedAuthority(role.trim());
		// ApplicationUser appUser = new ApplicationUser(userName, password, true, true,
		// true, true, grantedAuths,
		// "TestEmail");
		// auth = new UsernamePasswordAuthenticationToken(appUser, password,
		// grantedAuths);
		return auth;
		// }else{
		// return null;
		// }
	}

}
