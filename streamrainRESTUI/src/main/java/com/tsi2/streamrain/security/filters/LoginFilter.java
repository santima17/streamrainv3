package com.tsi2.streamrain.security.filters;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsi2.streamrain.security.user.User;
import com.tsi2.streamrain.security.utils.JwtUtil;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

	public LoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {

        InputStream body = req.getInputStream();

        // Realizamos un mapeo a nuestra clase User para tener ahi los datos
        User user = new ObjectMapper().readValue(body, User.class);

        // Spring comparará el user/password recibidos contra el que definimos en la clase SecurityConfig
        return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {

        // Si la autenticacion fue ok, agregamos el token a la respuesta
        JwtUtil.addAuthentication(res, auth.getName());
    }


}
