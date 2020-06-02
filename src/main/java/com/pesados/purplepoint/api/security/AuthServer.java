package com.pesados.purplepoint.api.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import  com.pesados.purplepoint.api.security.firebase.FirebaseParser;

@Configuration
public class AuthServer extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				//.addFilterAfter(new FirebaseAuthorizationFilter(new FirebaseParser()), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new CORSFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
                    .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/api-docs/**").permitAll()
			      	.antMatchers(HttpMethod.OPTIONS,"/**").permitAll() //allow CORS option calls
				.anyRequest().permitAll()
				.and()
				.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs",
				"/configuration/ui",
				"/swagger-resources/",
				"/configuration/security",
				"/swagger-ui.html",
				"/webjars/**");
	}
}