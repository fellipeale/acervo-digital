package com.fellipe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("fellipe").password("fellipe").roles("USER")
			.and()
			.withUser("zeca").password("zeca").roles("ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().authorizeRequests()
			.antMatchers(HttpMethod.GET, "/api/libraries/**").permitAll()
			.antMatchers("/api/libraries/**").hasRole("USER")
			.antMatchers(HttpMethod.GET, "/api/fields/**").permitAll()
			.antMatchers("/api/fields/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/api/records/**").permitAll()
			.antMatchers("/api/records/**").hasRole("ADMIN")
			.and().csrf().disable();
	}
	
}
