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
import com.tsi2.streamrain.security.filters.LoginFilter;

//@Configuration
//@EnableWebSecurity
//@Order(2)
public class GeneratorSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	AuthenticationManager generatorAuthenticationManager;
	
	@Override
    protected AuthenticationManager authenticationManager() {
        return generatorAuthenticationManager;
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/index.jsp").permitAll() // permitimos el acceso a /login a
																					// cualquiera
				.anyRequest().authenticated() // cualquier otra peticion requiere autenticacion
				.and()
				// Las peticiones /login pasaran previamente por este filtro
				.addFilterBefore(new LoginFilter("/generator/login", authenticationManager()),
						UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Creamos una cuenta de usuario por default
		auth.inMemoryAuthentication().withUser("ask").password("123").roles("ADMIN");
	}
}
