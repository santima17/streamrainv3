package com.tsi2.streamrain.security.utils;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class JwtUtil {

	private static final String TOKEN_HEADER = "Authorization";
	private static final String TOKEN_PREFIX = "TOKEN:";
	private static final String KEY = "TSId@s";

	// Crea JWT y lo envía al cliente en el header de la respuesta
	public static void addAuthentication(HttpServletResponse res, String username) {

		String token = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + 60000))
				.signWith(SignatureAlgorithm.HS512, KEY).compact();
		res.addHeader(TOKEN_HEADER, TOKEN_PREFIX + token);
	}

	// Valída el JWT enviado por el cliente
	public static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(TOKEN_HEADER);
		if (token != null) {
			String user = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody().getSubject();
			// para las demás peticiones que no sean /login
			// no requerimos una autenticacion por username/password
			// por este motivo podemos devolver un UsernamePasswordAuthenticationToken sin
			// password
			return user != null ? new UsernamePasswordAuthenticationToken(user, null, new ArrayList<GrantedAuthority>()) : null;
		}
		return null;
	}
}
