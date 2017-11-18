package com.tsi2.streamrain.security.filters;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.tsi2.streamrain.security.authentication.managers.GeneratorAuthenticationManager;
import com.tsi2.streamrain.security.authentication.managers.UserAuthenticationManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsi2.streamrain.security.user.User;
import com.tsi2.streamrain.security.utils.JwtUtil;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

	public LoginFilter(final String url, final AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(final HttpServletRequest req, final HttpServletResponse res)
			throws AuthenticationException, IOException, ServletException {

		String url = req.getRequestURL().toString();
		String tenantID = url.substring(7,url.indexOf("."));
		InputStream body = req.getInputStream();

		
		User user = new ObjectMapper().readValue(body, User.class);

		UsernamePasswordAuthenticationToken userToAuth = new UsernamePasswordAuthenticationToken(user.getUsername(),
				user.getPassword(), Collections.emptyList());

		if (getAuthenticationManager() instanceof UserAuthenticationManager) {
			((UserAuthenticationManager) getAuthenticationManager()).getSessionService().setCurrentTenant(tenantID);
			if(user.isTwitter()) {
				((UserAuthenticationManager) getAuthenticationManager()).setTwitterLogin(true);
				((UserAuthenticationManager) getAuthenticationManager()).setTwitterID(user.getTwitterID());
			}
		}else if (getAuthenticationManager() instanceof GeneratorAuthenticationManager) {
			((GeneratorAuthenticationManager) getAuthenticationManager()).getSessionService().setCurrentTenant(tenantID);
		}

		return getAuthenticationManager().authenticate(userToAuth);
	}

	@Override
	protected void successfulAuthentication(final HttpServletRequest req, final HttpServletResponse res,
			final FilterChain chain, final Authentication auth) throws IOException, ServletException {
		JwtUtil.addAuthentication(res, auth.getName());
	}

}
