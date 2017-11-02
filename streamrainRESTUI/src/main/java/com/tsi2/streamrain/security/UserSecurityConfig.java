package com.tsi2.streamrain.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tsi2.streamrain.security.filters.JwtFilter;
import com.tsi2.streamrain.security.filters.LoginFilter;

@Configuration
@EnableWebSecurity
@Order(1)
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	AuthenticationManager userAuthenticationManager;
	
	@Override
    protected AuthenticationManager authenticationManager() {
        return userAuthenticationManager;
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/*").permitAll(); // permitimos el acceso a /login a
																					// cualquiera
//				.antMatchers("/user/*").authenticated() // cualquier otra peticion requiere autenticacion
//				.and()
				// Las peticiones /login pasaran previamente por este filtro
//				.addFilterBefore(new LoginFilter("/user/login", authenticationManager()),
//						UsernamePasswordAuthenticationFilter.class)
//				// Las demás peticiones pasarán por este filtro para validar el token
//				.addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Creamos una cuenta de usuario por default
		auth.inMemoryAuthentication().withUser("ask").password("123").roles("ADMIN");
	}
}
