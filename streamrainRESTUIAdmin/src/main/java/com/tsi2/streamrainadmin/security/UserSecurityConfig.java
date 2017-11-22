package com.tsi2.streamrainadmin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.tsi2.streamrainadmin.security.filters.JwtFilter;
import com.tsi2.streamrainadmin.security.filters.LoginFilter;

//@Configuration
//@EnableWebSecurity
//@Order(1)
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	AuthenticationManager userAuthenticationManager;

	@Override
	protected AuthenticationManager authenticationManager() {
		return userAuthenticationManager;
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
				    
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.cors().and()
		.csrf().disable().authorizeRequests()
		.antMatchers("/index.jsp").permitAll()
		.antMatchers(HttpMethod.POST, "/user").permitAll()
		.antMatchers(HttpMethod.POST, "/user/login").permitAll()
		.antMatchers(HttpMethod.OPTIONS, "/user/login").permitAll()
		.antMatchers("/user/*").authenticated().and()
				// Las peticiones /login pasaran previamente por este filtro
		.addFilterBefore(new LoginFilter("/user/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration config = new CorsConfiguration();
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		config.addAllowedOrigin("*");
	    config.addAllowedHeader("*");
	    config.addAllowedMethod("GET");
	    config.addAllowedMethod("PUT");
	    config.addAllowedMethod("POST");
	    source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
	}
}
