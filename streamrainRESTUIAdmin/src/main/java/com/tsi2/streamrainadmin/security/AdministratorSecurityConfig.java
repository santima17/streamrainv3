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

@Configuration
@EnableWebSecurity
@Order(1)
public class AdministratorSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	AuthenticationManager administratorAuthenticationManager;

	@Override
	protected AuthenticationManager authenticationManager() {
		return administratorAuthenticationManager;
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
				//http.csrf().disable().authorizeRequests().antMatchers("/index.jsp").permitAll().antMatchers("/administrator/*")
				//.authenticated() // cualquier otra peticion requiere autenticacion
				//.and()
				http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.cors().and()
				.csrf().disable().authorizeRequests()
				.antMatchers("/index.jsp").permitAll()
				.antMatchers(HttpMethod.POST, "/administrator").permitAll()
				.antMatchers(HttpMethod.POST, "/administrator/login").permitAll()
				.antMatchers(HttpMethod.OPTIONS, "/administrator/login").permitAll()
				.antMatchers("/user/*").authenticated().and()
				// Las peticiones /login pasaran previamente por este filtro
				.addFilterBefore(new LoginFilter("/administrator/login", authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)
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
		// Creamos una cuenta de usuario por default
//		auth.inMemoryAuthentication().withUser("ask").password("123").roles("ADMIN");
	}
}
